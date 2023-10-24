package com.kh.Freepets.service.board.notice;

import com.kh.Freepets.domain.board.notice.NoticeLike;
import com.kh.Freepets.repo.board.notice.NoticeLikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoticeLikeService
{

    @Autowired
    private NoticeLikeDAO dao;


    public List<NoticeLike> showAllNoticeLike()
    {
        return dao.findAll();
    }


    public NoticeLike showNoticeLike(int noticelikeCode)
    {

        NoticeLike noticelike = dao.findById(noticelikeCode).orElse(null);
        return noticelike;
    }

    public NoticeLike showById(String id, int noticeCode)
    {
        try
        {
            NoticeLike noticeLike = dao.findById(id, noticeCode);
            return noticeLike;
        }
        catch (Exception e)
        {
            return null;
        }


    }


    public NoticeLike create(NoticeLike noticelike)
    {
        return dao.save(noticelike);
    }


    public NoticeLike update(NoticeLike noticelike)
    {
        NoticeLike target = dao.findById(noticelike.getNoticeLikeCode()).orElse(null);
        if (target != null)
        {
            return dao.save(noticelike);
        }
        return null;
    }


    public NoticeLike delete(int noticelikeCode)
    {
        NoticeLike target = dao.findById(noticelikeCode).orElse(null);
        dao.delete(target);
        return target;
    }

    public NoticeLike noDoubleLike(String id, int noticeCode)
    {
        NoticeLike noticelike = dao.noDoubleLike(id, noticeCode);
        return noticelike;
    }
}
