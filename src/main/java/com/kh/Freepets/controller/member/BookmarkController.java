package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.member.Bookmark;
import com.kh.Freepets.repo.board.BoardType;
import com.kh.Freepets.service.member.BookmarkService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/api/*")
public class BookmarkController
{
    @Autowired
    BookmarkService bookmarkService;


    @GetMapping("/api/bookmark")
    public ResponseEntity<List<Bookmark>> showAll()
    {  // 모든 북마크 가져오기
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.showAll());
    }


    @GetMapping("/api/bookmark/{id}")
    public ResponseEntity<List<Bookmark>> show(@PathVariable String id)
    {  // 특정 유저 북마크 가져오기
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.findByMemberId(id));
    }
    
//    // 특정 유저의 특정 게시판 가져오기
//    @GetMapping("/api/bookmark/{id}/{board_name}")
//    public ResponseEntity<List<Bookmark>> showByBoard(@PathVariable String id, @PathVariable String board_name)
//    {// 게시판 별 북마크 보기 프론트에서 board_name -> String 문자열로 넘길때
//        List<Bookmark> lists = bookmarkService.findByBoard(id, BoardType.getType(board_name));
//        return ResponseEntity.status(HttpStatus.OK).body(lists);
//    }

    @GetMapping("/api/bookmark/{id}/{board_code}")
    public ResponseEntity<List<Bookmark>> showByBoard(@PathVariable String id, @PathVariable int board_code)
    {// 게시판 별 북마크 보기 프론트에서 code -> int값으로 넘길때
        List<Bookmark> lists = bookmarkService.findByBoard(id, BoardType.getType(board_code));
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @PostMapping("/api/bookmark")
    public ResponseEntity<Bookmark> create(@RequestBody Bookmark bookmark)
    {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.create(bookmark));
    }


    @PutMapping("/api/bookmark")
    public ResponseEntity<Bookmark> update(@RequestBody Bookmark bookmark)
    {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.update(bookmark));
    }

    @DeleteMapping("/api/bookmark/{code}")
    public ResponseEntity<Bookmark> delete(@PathVariable int code)
    {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.delete(code));
    }




}
