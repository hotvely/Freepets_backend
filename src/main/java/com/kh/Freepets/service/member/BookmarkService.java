package com.kh.Freepets.service.member;

import com.kh.Freepets.domain.member.Bookmark;
import com.kh.Freepets.repo.board.BoardType;
import com.kh.Freepets.repo.member.BookmarkDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
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

    // 특정 유저의 게시판 별 북마크리스트 부르기 !
    public List<Bookmark> findByBoard(String id, BoardType type)
    {
        List<Bookmark> bookmarks = findByMemberId(id);
        List<Bookmark> bookmarkByBoardType = new ArrayList<>();

        for(Bookmark elem : bookmarks)
        {
            if(elem.getBoard_code() == type.getValue()){
                bookmarkByBoardType.add(elem);
            }
        }

        return bookmarkByBoardType;
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
        dao.delete(target);
        return target;
    }



}
