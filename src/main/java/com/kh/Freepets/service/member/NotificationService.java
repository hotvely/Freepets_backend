package com.kh.Freepets.service.member;

import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.domain.board.sitter.Sitter;
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
public class NotificationService
{
    @Autowired
    private NotificationDAO dao;

    @Autowired
    private MemberService memberService;


    @Autowired
    private NoticeService noticeService;


    // 멤버별 알림 가져오기..
    public List<Notification> showNotiByMember(String id)
    {
        return dao.showNotiByMember(id);
    }

    public Notification showBcodePcode(int boardCode, int postCode)
    {
        return dao.showBcodePcode(boardCode, postCode);
    }

    public Notification showParentCode(int boardCode, int postCode, int parentCode)
    {
        return dao.showBoardPostParentCode(boardCode, postCode, parentCode);
    }


    // 알림 DB추가
    public Notification save(Notification notification)
    {
        return dao.save(notification);
    }

    // 알림 삭제하기..
    public Notification deleteNoti(int code)
    {
        Notification target = dao.findById(code).orElse(null);

        if (target != null)
        {
            dao.delete(target);
            return target;
        }

        return null;
    }


    public CommentDTO createCommentDTO(NoticeComment noticeComment)
    {
        if (noticeComment != null)
        {

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

    public BoardDTO createBoardDTO(Object obj, BoardType type)
    {
        log.info(obj.toString());
        BoardDTO boardDTO = BoardDTO.builder().build();
        int typeCount = BoardType.getTypeCode(type);
        switch (typeCount)
        {
            case 1:
                boardDTO.setTitle(((Community) obj).getCommonTitle());
                break;
            case 2:
                boardDTO.setTitle(((Lost) obj).getLostTitle());
                break;
            case 3:
                boardDTO.setTitle(((Sitter) obj).getSitterTitle());
                break;
            case 4:
                boardDTO.setTitle(((HospitalReview) obj).getHospitalReviewTitle());
                break;
            case 5:
                boardDTO.setTitle(((Notice) obj).getNoticeTitle());
                break;

        }


        return boardDTO;
    }


}