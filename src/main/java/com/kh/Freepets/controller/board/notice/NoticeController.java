package com.kh.Freepets.controller.board.notice;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.notice.NoticeLike;
import com.kh.Freepets.domain.board.notice.NoticeLikeDTO;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.util.Paging;
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
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin
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
    public ResponseEntity<Paging> showAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "sortNum", defaultValue = "1") int sortNum)
    {
        log.info("get ShowALL 들어옴");
        try
        {
            Sort sort = null;
            switch (sortNum)
            {
                case 1:
                    sort = Sort.by("noticeCode").descending();
                    break;
                case 2:
                    sort = Sort.by("noticeLike").descending();
                    break;
                case 3:
                    sort = Sort.by("noticeCommentCount").descending();
                    break;
                case 4:
                    sort = Sort.by("noticeViews").descending();
                    break;
                default:
                    sort = Sort.by("noticeCode").descending();
                    break;
            }

            Pageable pageable = PageRequest.of(page - 1, 10, sort);
            Page<Notice> result = noticeService.showAll(pageable);
            log.info(result.toString());

            Paging paging = new Paging();
            paging.setNoticeList(result.getContent());
            paging.setTotalCount(result.getTotalElements());
            paging.setTotalPages(result.getTotalPages());
            paging.setGetNumber(result.getNumber());
            paging.setHasNext(result.hasNext());
            paging.setHasPrev(result.hasPrevious());
            paging.setFirst(result.isFirst());

//            log.info("게시글 페이지징 별로 보기.. : " + result.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(paging);
        }
        catch (Exception e)
        {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @GetMapping("/notice/search")
    public ResponseEntity<Paging> findByKeyword(@RequestParam(name = "keyword") String keyword, @RequestParam(name = "searchNum") int searchNum,
                                                @RequestParam(name = "page") int page, @RequestParam(name = "sortNum") int sortNum)
    {
        String word = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        log.info(word);
        try
        {
            Page<Notice> result = null;
            Sort sort = null;
            switch (sortNum)
            {
                case 1:
                    sort = Sort.by("NOTICE_CODE").descending();
                    break;
                case 2:
                    sort = Sort.by("NOTICE_LIKE").descending().and(Sort.by("NOTICE_CODE").descending());
                    break;
                case 3:
                    sort = Sort.by("NOTICE_COMMENT_COUNT").descending().and(Sort.by("NOTICE_CODE").descending());
                    break;
                case 4:
                    sort = Sort.by("NOTICE_VIEWS").descending().and(Sort.by("NOTICE_CODE").descending());
                    break;
                default:
                    sort = Sort.by("NOTICE_CODE").descending().and(Sort.by("NOTICE_CODE").descending());
                    break;
            }

            switch (searchNum)
            {
                case 1:
                    log.info("제목 댓글..");
                    Pageable pageable = PageRequest.of(page - 1, 10, sort);
                    result = noticeService.searchTitleContent(word, pageable);
                    break;
                case 2:
                    log.info("제목..");
                    pageable = PageRequest.of(page - 1, 10, sort);
                    result = noticeService.searchTitle(word, pageable);
                    break;
                case 3:
                    log.info("댓글..");
//                    sort = sort.and(Sort.by("searchNum").descending());
                    pageable = PageRequest.of(page - 1, 10, sort);
                    result = noticeService.searchContent(word, pageable);
                    break;
                default:
                    log.info("기본..");

                    break;

            }

            Paging paging = new Paging();
            paging.setNoticeList(result.getContent());
            paging.setTotalCount(result.getTotalElements());
            paging.setTotalPages(result.getTotalPages());
            paging.setGetNumber(result.getNumber());
            paging.setHasNext(result.hasNext());
            paging.setHasPrev(result.hasPrevious());
            paging.setFirst(result.isFirst());
            log.info(paging.toString());
            return ResponseEntity.ok().body(paging);
        }
        catch (Exception e)
        {
            log.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }


    // 공지사항 게시글 상세보기
    @GetMapping("/notice/{noticeCode}")
    public ResponseEntity<Notice> showNotice(@PathVariable int noticeCode)
    {
        Notice vo = noticeService.show(noticeCode);
        vo.setNoticeViews(vo.getNoticeViews() + 1);

        Notice updatedVo = noticeService.update(vo);


        return ResponseEntity.status(HttpStatus.OK).body(updatedVo);
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

        Notice vo = Notice.builder()
                .noticeCode(boardDTO.getBoardCode())
                .noticeTitle(boardDTO.getTitle())
                .noticeAddFileUrl(boardDTO.getUploadfileUrl())
                .noticeDesc(boardDTO.getDesc())
                .noticeCommentCount(boardDTO.getCommentCount())
                .noticeLike(boardDTO.getLikeCount())

                .build();
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.update(vo));

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
    public ResponseEntity<NoticeLike> createNoticeLike(@RequestBody NoticeLikeDTO dto)
    {
        //
        log.info("공지사항 좋아요 추가 삭제");
        log.info(dto.getToken());
        log.info("게시글번호 : " + dto.getPostCode());
        String userId = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(userId);
        NoticeLike target = noticelikeservice.showById(userId, dto.getPostCode());

        // DB에 타켓 라이크 정보가 없거나, DB에서 가져온 정보가 들어온 유저하고 게시글 정보하고 같지 않을때만 추가
        if (target == null)     //가장 처음 넣어서 DB에 아예 정보가 없어가지고 첫 좋아요 누를때..
        {
            log.info("target이 널임 !!");
            Notice notice = noticeService.updatelike(dto.getPostCode());
            NoticeLike noticeLike = NoticeLike.builder()
                    .noticeLikeDate(new Date())
                    .member(member)
                    .notice(notice)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(noticelikeservice.create(noticeLike));

        }
        else        //DB에 정보가 있는 경우 처리 !
        {
            log.info("target이 널아니앙야아아아아@#@@ !!");
            // 전달받은 아이디,게시글코드 하고 공지사항좋아요 DB에 있는 아이디,게시글 코드가 같으면 삭제하고
            if (target.getMember().getId().equals(userId) && target.getNotice().getNoticeCode() == dto.getPostCode())
            {
                Notice notice = noticeService.deletelike(dto.getPostCode());
                NoticeLike noticeLike = noticelikeservice.delete(target.getNoticeLikeCode());


                return ResponseEntity.ok().body(null);
            }
            else
            {
                //유저 이름이 다르거나, 게시글 번호가 다르거나 등과 같은 현상이 일어남..
                return ResponseEntity.ok().build();
            }
            // 근데 생각해 보면.. 추가하려고 또 했는데.. 눌린거면 걍 삭제 아님?

        }


    }


    // 공지사항 좋아요 삭제 DELETE - http://localhost:8080/api/notice/like/{noticeLikeCode}
    // 좋아요 갯수 차감 (NoticeDAO에 쿼리문 작성 )
    // 동시에 좋아요 갯수 중복 처리 (NoticeLikeDAO에 쿼리문 작성 )
//    @DeleteMapping("/notice/like/{id}")
//    public ResponseEntity<NoticeLike> deleteNoticeLike(@PathVariable int noticeLikeCode)
//    {
//
//        NoticeLike noticelike = noticelikeservice.showNoticeLike(noticeLikeCode);
//        NoticeLike target = noticelikeservice.noDoubleLike(noticelike.getNotice().getMember().getId(), noticelike.getNotice().getNoticeCode());
//
//        if (target != null)
//        {
//            noticeService.deletelike(noticelike.getNotice().getNoticeCode());
//            return ResponseEntity.status(HttpStatus.OK).body(noticelikeservice.delete(noticeLikeCode));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }


}
