package com.kh.Freepets.service.member;

import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.domain.member.Notification;
import com.kh.Freepets.repo.member.NotificationDAO;
import com.kh.Freepets.service.board.notice.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationDAO dao;

    @Autowired
    private MemberService memberService;


    @Autowired
    private NoticeService noticeService;


    // 멤버별 알림 가져오기..
    public List<Notification> showNotiByMember(String id) {
        return dao.showNotiByMember(id);
    }

    // 알림 DB추가
    public Notification save(Notification notification) {
        return dao.save(notification);
    }

    // 알림 삭제하기..
    public Notification deleteNoti(int code) {
        Notification target = dao.findById(code).orElse(null);

        if (target != null) {
            dao.delete(target);
            return target;
        }

        return null;
    }

    public CommentDTO createCommentDTO(NoticeComment noticeComment) {
        if (noticeComment != null) {

            MemberDTO memberDTO = memberService.createDTO(noticeComment.getMember(), null);

            CommentDTO commentDTO = CommentDTO.builder()
                    .commentCode(noticeComment.getNoticeCommentCode())
                    .commentDesc(noticeComment.getNoticeCommentDesc())
                    .boardName(BoardType.getTypeName(BoardType.notice))
                    .postCode(noticeComment.getNotice().getNoticeCode())
                    .parentCommentCode(noticeComment.getNoticeCommentCodeSuper())
                    .memberDTO(memberDTO).build();


            return commentDTO;
        }
        return null;

    }

    public BoardDTO createBoardDTO(int boardCode, int postCode) {
        BoardDTO boardDTO = BoardDTO.builder().build();

        switch (boardCode) {
            case 1:

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                Notice notice = noticeService.show(postCode);
                boardDTO.setTitle(notice.getNoticeTitle());
                break;

        }


        return boardDTO;
    }


}