package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.member.Bookmark;
import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.member.BookmarkDTO;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.notice.NoticeService;
import com.kh.Freepets.service.member.BookmarkService;
import com.kh.Freepets.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/*")
@CrossOrigin
public class BookmarkController
{
    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    MemberService memberService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    TokenProvider tokenProvider;

    @GetMapping("/bookmark")
    public ResponseEntity<List<Bookmark>> showAll()
    {  // 모든 북마크 가져오기
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.showAll());
    }


    @GetMapping("/bookmark/{id}")
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

    @GetMapping("/bookmark/{id}/{boardName}")
    public ResponseEntity<List<Bookmark>> showByBoard(@PathVariable String id, @PathVariable String boardName)
    {// 게시판 별 북마크 보기 프론트에서 code -> int값으로 넘길때

        BoardType boardType = BoardType.getType(boardName);
        int typeCode = BoardType.getTypeCode(boardType);

        if (typeCode > 0)
        {// 게시판 이름 받아서 Board Type으로 바꿔주고 code값으로 리턴 받아서 양수 인경우에는 알맞는 데이터 들어옴!
            List<Bookmark> lists = bookmarkService.findBy_id_code(id, typeCode);
            return ResponseEntity.ok().body(lists);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/bookmark")
    public ResponseEntity<Bookmark> create(@RequestBody BookmarkDTO bookmarkDTO)
    {
        log.info("BOOKMARK : " + bookmarkDTO.toString());

        String userId = tokenProvider.validateAndGetUserId(bookmarkDTO.getToken());
        Member member = memberService.findByIdUser(userId);


        int code = BoardType.getTypeCode(BoardType.getType(bookmarkDTO.getBoardName()));
        log.info("code : " + code);
        List<Bookmark> list = bookmarkService.findBy_id_code(userId, code);
        log.info("list@@@@@@@@@@@ : " + list.toString());

        for (Bookmark bm : list)
        {
            // 게시글이 이미 북마크에 있음;
            if (bm.getPostCode() == bookmarkDTO.getPostCode())
            {
                log.info("이미 들어가 있음..");
                return ResponseEntity.badRequest().build();
            }
        }

        Bookmark bookmark = Bookmark.builder()
                .boardCode(code)
                .postCode(bookmarkDTO.getPostCode())
                .member(member)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.create(bookmark));
    }


    @PutMapping("/bookmark")
    public ResponseEntity<Bookmark> update(@RequestBody Bookmark bookmark)
    {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.update(bookmark));
    }

    @DeleteMapping("/bookmark/{code}")
    public ResponseEntity<Bookmark> delete(@PathVariable int code)
    {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.delete(code));
    }


}
