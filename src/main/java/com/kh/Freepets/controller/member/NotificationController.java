package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.domain.member.Notification;
import com.kh.Freepets.domain.member.NotificationDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityService;
import com.kh.Freepets.service.board.community.LostService;
import com.kh.Freepets.service.board.information.HospitalReviewService;
import com.kh.Freepets.service.board.notice.NoticeCommentService;
import com.kh.Freepets.service.chatting.ChattingService;
import com.kh.Freepets.service.member.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class NotificationController
{

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private LostService lostService;

    @Autowired
    private HospitalReviewService hospitalReviewService;

    @Autowired
    private NoticeCommentService noticeCommentService;


    @Autowired
    private ChattingService chattingService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/noti/{token}")
    public ResponseEntity<List<NotificationDTO>> showByMember(@PathVariable String token)
    {

        String userId = tokenProvider.validateAndGetUserId(token);
        log.info(userId);
        List<Notification> notiList = notificationService.showNotiByMember(userId);
        List<NotificationDTO> dtoList = new ArrayList<>();

        for (Notification elem : notiList)
        {
            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .code(elem.getNotiCode())
                    .id(userId)
                    .url(elem.getUrl())
                    .postCode(elem.getPostCode())
                    .boardCode(elem.getBoardCode())
                    .build();

            CommentDTO childCommentDTO = null;
            CommentDTO parentCommentDTO = null;
            BoardDTO boardDTO = null;

            switch (elem.getBoardCode())
            {
                case 1:
                {

                }
                break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                {
                    NoticeComment childNoticeComment = noticeCommentService.showComment(elem.getChildCommentCode());
                    NoticeComment parentNoticeComment = null;

                    childCommentDTO = notificationService.createCommentDTO(childNoticeComment);

                    if (elem.getParentCommentCode() > 0)
                    {
                        parentNoticeComment = noticeCommentService.showComment(elem.getParentCommentCode());

                        parentCommentDTO = notificationService.createCommentDTO(parentNoticeComment);
                    }

                    boardDTO = notificationService.createBoardDTO(elem.getBoardCode(), elem.getPostCode());        //제목이랑 정도?
                    notificationDTO.setChildComment(childCommentDTO);
                    notificationDTO.setParentComment(parentCommentDTO);
                    notificationDTO.setBoardDTO(boardDTO);


                }
                break;
                case 6:


                    break;
            }

            dtoList.add(notificationDTO);

        }

        return ResponseEntity.ok().body(dtoList);
    }

    @PostMapping("/noti/register")
    public ResponseEntity<Notification> save(@RequestBody NotificationDTO dto)
    {
        String userId = tokenProvider.validateAndGetUserId(dto.getToken());
        Notification vo = Notification.builder()
                .id(userId)
                .boardCode(dto.getBoardCode())
                .postCode(dto.getPostCode())
                .childCommentCode(dto.getChildCommentCode())
                .parentCommentCode(dto.getParentCommentCode())
                .url(dto.getUrl())
                .build();

        return ResponseEntity.ok().body(notificationService.save(vo));
    }

    @DeleteMapping("/noti/{code}")
    public ResponseEntity<Boolean> delete(@PathVariable int code)
    {
        Notification target = notificationService.deleteNoti(code);

        if (target != null)
        {
            return ResponseEntity.ok().body(true);
        }

        return ResponseEntity.badRequest().body(false);

    }


}