package com.kh.Freepets.controller.member;

import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.chatting.Chatting;
import com.kh.Freepets.domain.member.Notification;
import com.kh.Freepets.domain.member.NotificationDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityService;
import com.kh.Freepets.service.board.community.LostService;
import com.kh.Freepets.service.board.information.HospitalReviewService;
import com.kh.Freepets.service.board.notice.NoticeCommentService;
import com.kh.Freepets.service.board.notice.NoticeService;
import com.kh.Freepets.service.board.sitter.SitterService;
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
    private SitterService sitterService;
    @Autowired
    private HospitalReviewService hospitalReviewService;
    @Autowired
    private NoticeService noticeService;
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
                    Community community = communityService.showCommon(elem.getPostCode());
                    if (community == null)
                    {
                        Notification notification
                                = notificationService.showBcodePcode(elem.getBoardCode(), elem.getPostCode());

                        notificationService.deleteNoti(notification.getNotiCode());
                        break;
                    }
                    dtoList.add(notificationDTO);
                }
                break;
                case 2:
                    Lost lost = lostService.showlost(elem.getPostCode());
                    if (lost == null)
                    {
                        Notification notification
                                = notificationService.showBcodePcode(elem.getBoardCode(), elem.getPostCode());

                        notificationService.deleteNoti(notification.getNotiCode());
                        break;
                    }
                    dtoList.add(notificationDTO);
                    break;
                case 3:
                    Sitter sitter = sitterService.show(elem.getPostCode());
                    if (sitter == null)
                    {
                        Notification notification
                                = notificationService.showBcodePcode(elem.getBoardCode(), elem.getPostCode());

                        notificationService.deleteNoti(notification.getNotiCode());
                        break;
                    }
                    dtoList.add(notificationDTO);
                    break;
                case 4:
                    HospitalReview hospitalReview = hospitalReviewService.show(elem.getPostCode());
                    if (hospitalReview == null)
                    {
                        Notification notification
                                = notificationService.showBcodePcode(elem.getBoardCode(), elem.getPostCode());

                        notificationService.deleteNoti(notification.getNotiCode());
                        break;
                    }
                    boardDTO = notificationService.createBoardDTO(hospitalReview, BoardType.hospitalReview);        //제목이랑 정도?
      
                    notificationDTO.setBoardDTO(boardDTO);

                    dtoList.add(notificationDTO);
                    break;
                case 5:
                {
                    NoticeComment childNoticeComment = noticeCommentService.showComment(elem.getChildCommentCode());
                    NoticeComment parentNoticeComment = null;

                    childCommentDTO = notificationService.createCommentDTO(childNoticeComment);

                    if (elem.getParentCommentCode() > 0)
                    {
                        NoticeComment noticeComment = noticeCommentService.showComment(elem.getParentCommentCode());
                        if (noticeComment == null)
                        {
                            Notification notification =
                                    notificationService.showParentCode(elem.getBoardCode(), elem.getPostCode(), elem.getParentCommentCode());

                            notificationService.deleteNoti(notification.getNotiCode());
                            break;
                        }
                        parentNoticeComment = noticeCommentService.showComment(elem.getParentCommentCode());

                        parentCommentDTO = notificationService.createCommentDTO(parentNoticeComment);
                    }


                    Notice notice = noticeService.show(elem.getPostCode());
                    if (notice == null)
                    {
                        Notification notification
                                = notificationService.showBcodePcode(elem.getBoardCode(), elem.getPostCode());

                        notificationService.deleteNoti(notification.getNotiCode());
                        break;
                    }

                    boardDTO = notificationService.createBoardDTO(notice, BoardType.notice);        //제목이랑 정도?
                    notificationDTO.setChildComment(childCommentDTO);
                    notificationDTO.setParentComment(parentCommentDTO);
                    notificationDTO.setBoardDTO(boardDTO);
                    dtoList.add(notificationDTO);

                }
                break;
                case 6:
                    // 도경님 채팅 시퀀스로 받는 로직 피료함!
//                    Chatting chatting = chattingService.show(elem.getBoardCode());
//                    if (chatting == null)
//                    {
//                        Notification notification
//                                = notificationService.showBcodePcode(elem.getBoardCode(), elem.getPostCode());
//
//                        notificationService.deleteNoti(notification.getNotiCode());
//                        break;
//                    }
                    dtoList.add(notificationDTO);

                    break;
            }


        }

        return ResponseEntity.ok().body(dtoList);
    }

    @PostMapping("/noti/register")
    public ResponseEntity<Notification> save(@RequestBody NotificationDTO dto)
    {


        Notification vo = Notification.builder()
                .id(dto.getId())
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
