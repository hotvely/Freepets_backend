package com.kh.Freepets.controller.board.notice;

import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.domain.board.notice.NoticeLike;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.notice.NoticeCommentService;
import com.kh.Freepets.service.board.notice.NoticeLikeService;
import com.kh.Freepets.service.board.notice.NoticeService;
import com.kh.Freepets.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class NoticeController
{
    @Autowired
    private TokenProvider tokenProvider;


    @Autowired
    private NoticeService noticeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeLikeService noticelikeservice;

    @Autowired
    private NoticeCommentService noticeCommentService;

    // 공지사항 게시글 전체보기
    @GetMapping("/notice")
    public ResponseEntity<List<Notice>> showAll(@RequestParam(name = "page", defaultValue = "1") int page)
    {
        log.info("get ShowALL 들어옴");
        try
        {
            Sort sort = Sort.by("noticeCode").descending();
            Pageable pageable = PageRequest.of(page - 1, 10, sort);
            Page<Notice> result = noticeService.showAll(pageable);

//            log.info("게시글 페이지징 별로 보기.. : " + result.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/notice/search/{keyword}")
    public ResponseEntity<List<Notice>> findByKeyword(@RequestParam(name = "keyword") String keyword, @RequestParam(name = "page", defaultValue = "1") int page)
    {

        // URL 서치 관련해서 받는거 에러남;;;
        String s = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        log.info("keyword" + s);
        log.info("검색 관련 기능 드러옴");
        try
        {
            Sort sort = Sort.by("noticeCode").descending();
            Pageable pageable = PageRequest.of(page - 1, 10, sort);
            Page<Notice> result = noticeService.search(pageable, keyword);

//            log.info("게시글 페이지징 별로 보기.. : " + result.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }


    // 공지사항 게시글 상세보기
    @GetMapping("/notice/{noticeCode}")
    public ResponseEntity<Notice> showNotice(@PathVariable int noticeCode)
    {
        log.info("게시글 코드 :  " + noticeCode);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.show(noticeCode));
    }

    // 공지사항 게시글 추가하기
    @PostMapping("/notice")
    public ResponseEntity<Notice> createNotice(BoardDTO boardDTO)
    {
        log.info("프론트에서 받은 boardDTO : " + boardDTO.toString());

        String userId = tokenProvider.validateAndGetUserId(boardDTO.getToken());
        Member member = memberService.findByIdUser(userId);


        log.info("back에서 만든 member : " + member.toString());

        Notice vo = Notice.builder()
                .noticeCode(boardDTO.getBoardCode())
                .noticeTitle(boardDTO.getTitle())
                .noticeAddFileUrl(boardDTO.getUploadfileUrl())
                .noticeDesc(boardDTO.getDesc())
                .member(member)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(noticeService.create(vo));
    }

    // 공지사항 게시글 수정하기
    @PutMapping("/notice")
    public ResponseEntity<Notice> updateNotice(@RequestBody BoardDTO boardDTO)
    {
        log.info("UpdateNOTICE : " + boardDTO.toString());
        Notice dbNotice = noticeService.show(boardDTO.getBoardCode());
        String userId = tokenProvider.validateAndGetUserId(boardDTO.getToken());
        if (dbNotice.getMember().getId().equals(userId))
        {
            Member member = memberService.findByIdUser(userId);
            Notice vo = Notice.builder()
                    .noticeCode(boardDTO.getBoardCode())
                    .noticeTitle(boardDTO.getTitle())
                    .noticeAddFileUrl(boardDTO.getUploadfileUrl())
                    .noticeDesc(boardDTO.getDesc())
                    .member(member)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(noticeService.update(vo));
        }

        return ResponseEntity.badRequest().build();
    }

    // 공지사항 게시글 삭제하기
    @DeleteMapping("/notice/{noticeCode}")
    public ResponseEntity<Notice> deleteNotice(@PathVariable int noticeCode)
    {
        log.info("NOTICE CODE : " + noticeCode);


        return ResponseEntity.status(HttpStatus.OK).body(noticeService.delete(noticeCode));
    }

    // 공지사항 좋아요 추가  POST - http://localhost:8080/api/notice/like
    // 동시에 좋아요 갯수 추가 (NoticeDAO에 쿼리문 작성 )
    // 동시에 좋아요 갯수 중복 처리 (NoticeLikeDAO에 쿼리문 작성 )
    @PostMapping("/notice/like")
    public ResponseEntity<NoticeLike> createNoticeLike(@RequestBody NoticeLike noticelike)
    {
        NoticeLike target = noticelikeservice.noDoubleLike(noticelike.getNotice().getMember().getId(), noticelike.getNotice().getNoticeCode());


        if (target == null)
        {
            noticeService.updatelike(noticelike.getNotice().getNoticeCode());
            return ResponseEntity.status(HttpStatus.OK).body(noticelikeservice.create(noticelike));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 공지사항 좋아요 삭제 DELETE - http://localhost:8080/api/notice/like/{noticeLikeCode}
    // 좋아요 갯수 차감 (NoticeDAO에 쿼리문 작성 )
    // 동시에 좋아요 갯수 중복 처리 (NoticeLikeDAO에 쿼리문 작성 )
    @DeleteMapping("/notice/like/{id}")
    public ResponseEntity<NoticeLike> deleteNoticeLike(@PathVariable int noticeLikeCode)
    {

        NoticeLike noticelike = noticelikeservice.showNoticeLike(noticeLikeCode);
        NoticeLike target = noticelikeservice.noDoubleLike(noticelike.getNotice().getMember().getId(), noticelike.getNotice().getNoticeCode());

        if (target != null)
        {
            noticeService.deletelike(noticelike.getNotice().getNoticeCode());
            return ResponseEntity.status(HttpStatus.OK).body(noticelikeservice.delete(noticeLikeCode));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 댓글수 별 게시글 정렬 Get - http://localhost:8080/api/notice/sortcomment
    @GetMapping("/notice/sortcomment")
    public ResponseEntity<List<Notice>> sortCommentCount()
    {
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.sortCommentCount());
    }

    // 좋아요수 별 게시글 정렬 Get - http://localhost:8080/api/notice/sortlike
    @GetMapping("/notice/sortlike")
    public ResponseEntity<List<Notice>> sortNoticeLike()
    {
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.sortNoticeLike());
    }

    // 공지사항 조회순 정렬 GET - http://localhost:8080/api/notice/sortviews
    @GetMapping("/notice/sortviews")
    public ResponseEntity<List<Notice>> sortNoticeViews()
    {
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.sortNoticeViews());
    }


}
