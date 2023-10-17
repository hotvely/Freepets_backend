package com.kh.Freepets.service.board.notice;

import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.repo.board.notice.NoticeCommentDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoticeCommentService
{

    @Autowired
    private NoticeCommentDAO dao;


    public List<NoticeComment> showAll()
    {
        return dao.findAll();
    }

    public NoticeComment showNoticeComment(int lCommentCode)
    {
        NoticeComment noticecomment = dao.findById(lCommentCode).orElse(null);
        return noticecomment;
    }

    public NoticeComment create(NoticeComment noticecomment)
    {
        return dao.save(noticecomment);
    }

    public NoticeComment update(NoticeComment noticecomment)
    {
        NoticeComment target = dao.findById(noticecomment.getNoticeCommentCode()).orElse(null);
        if (target != null)
        {
            return dao.save(target);
        }
        return null;
    }

    public NoticeComment delete(int lCommentCode)
    {
        NoticeComment target = dao.findById(lCommentCode).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<NoticeComment> findByNoticeCode(int code)
    {

        return dao.findByNoticeCode(code);
    }


}

