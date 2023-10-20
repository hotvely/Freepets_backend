package com.kh.Freepets.service.board.notice;

import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.notice.NoticeDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoticeService
{
    @Autowired
    private NoticeDAO dao;

    @Autowired
    private MemberDAO memberdao;

    public Page<Notice> showAll(Pageable pageable)
    {
        return dao.findAll(pageable);
    }

    public Notice show(int noticeCode)
    {
        Notice notice = dao.findById(noticeCode).orElse(null);
        // notice.setNoticeViews(notice.getNoticeViews()+1);  // 조회수 증가 메소드
        return notice;
    }


    public Notice create(Notice notice)
    {
        Member member = memberdao.findById(notice.getMember().getId()).orElse(null);
        notice.setMember(member);
        return dao.save(notice);
    }

    public Notice update(Notice notice)
    {
        Notice target = dao.findById(notice.getNoticeCode()).orElse(null);
        if (target != null)
        {
            target.setNoticeTitle(notice.getNoticeTitle());
            target.setNoticeDesc(notice.getNoticeDesc());
            return dao.save(target);
        }
        return null;
    }

    public Notice delete(int noticeCode)
    {
        Notice target = dao.findById(noticeCode).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<Notice> showMember(String id)
    { //특정 유저의 분실 게시물 조회
        Member member = memberdao.findById(id).orElse(null);
        List<Notice> target = dao.findByMemberId(member.getId());
        return target;
    }

    // 게시글 좋아요 갯수 업데이트
    public Notice updatelike(int noticeCode)
    {
        dao.updateNoticelike(noticeCode);
        return dao.findById(noticeCode).orElse(null);

    }

    // 게시글 좋아요 갯수 차감
    public Notice deletelike(int noticeCode)
    {
        dao.deleteNoticelike(noticeCode);
        return dao.findById(noticeCode).orElse(null);
    }

    // 게시글 댓글 갯수 업데이트
    public Notice updateNoticeCommentCount(int noticeCode)
    {
        dao.updateNoticeCommentCount(noticeCode);
        return dao.findById(noticeCode).orElse(null);
    }


    // 게시글 댓글 갯수 차감
    public Notice deleteNoticeCommentCount(int noticeCode)
    {
        dao.deleteNoticeCommentCount(noticeCode);
        return dao.findById(noticeCode).orElse(null);
    }

    // 게시글 댓글 갯수 정렬
    public List<Notice> sortCommentCount()
    {
        return dao.sortCommentCount();
    }


    // 게시글 좋아요 수 정렬
    public List<Notice> sortNoticeLike()
    {
        return dao.sortNoticeLike();
    }

    // 분실 게시글 조회순 정렬
    public List<Notice> sortNoticeViews()
    {
        return dao.sortNoticeViews();

    }


}
