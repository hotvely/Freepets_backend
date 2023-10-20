package com.kh.Freepets.service.member;

import com.kh.Freepets.domain.member.Bookmark;
import com.kh.Freepets.BoardType;
import com.kh.Freepets.repo.member.BookmarkDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class BookmarkService
{
    @Autowired
    private BookmarkDAO dao;

    // 전체 북마크 부르기.
    public List<Bookmark> showAll()
    {
        return dao.findAll();
    }


    // 특정 유저의 북마크 부르기
    public List<Bookmark> findByMemberId(String id)
    {
        return dao.findByMemberId(id);
    }

    public List<Bookmark> findBy_id_code(String id, int code)
    {
        return dao.findBy_id_code(id, code);
    }

    public Bookmark create(Bookmark bookmark)
    {
        return dao.save(bookmark);
    }

    public Bookmark update(Bookmark bookmark)
    {
        return dao.save(bookmark);
    }

    public Bookmark delete(int id)
    {
        Bookmark target = dao.findById(id).orElse(null);
        if (target != null)
            dao.delete(target);

        return target;
    }


}
