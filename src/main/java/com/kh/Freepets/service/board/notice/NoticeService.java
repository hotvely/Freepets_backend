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


    public Page<Notice> searchTitleContent(String keyword, Pageable pageable)
    {
        return dao.searchTitleContent(keyword, pageable);
    }

    public Page<Notice> searchTitle(String keyword, Pageable pageable)
    {
        return dao.searchTitle(keyword, pageable);
    }

    public Page<Notice> searchContent(String keyword, Pageable pageable)
    {
        return dao.searchContent(keyword, pageable);
    }


    public Notice show(int noticeCode)
    {
        // notice.setNoticeViews(notice.getNoticeViews()+1);  // 조회수 증가 메소드
        return dao.findById(noticeCode).orElse(null);
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

    public void updateCommentCount(int noticeCode)
    {
        dao.updateNoticeCommentCount(noticeCode);
    }

    // 게시글 좋아요 갯수 차감
    public Notice deletelike(int noticeCode)
    {
        dao.deleteNoticelike(noticeCode);
        return dao.findById(noticeCode).orElse(null);
    }


}
