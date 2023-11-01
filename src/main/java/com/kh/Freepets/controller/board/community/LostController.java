package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.community.*;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.util.Paging;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.LostLikeService;
import com.kh.Freepets.service.board.community.LostService;
import com.kh.Freepets.service.file.FileInputHandler;
import com.kh.Freepets.service.member.MemberService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = {"*"},maxAge = 6000)
@RestController
@RequestMapping("/api/*")
public class LostController {
    @Autowired
    private LostService lostService;
    @Autowired
    private LostLikeService lostLikeService;
    @Autowired
    private FileInputHandler handler;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JPAQueryFactory queryFactory;

    //일반게시판 전체 조회 GET - http://localhost:8080/api/community/lost
    //페이징 처리
    @GetMapping("/community/lost")
    public ResponseEntity<Paging> lostList(
            @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "orderBy", defaultValue = "1") int orderBy) {
        Sort sort;

        switch (orderBy) {
            case 1:
                sort = Sort.by("lostCode").descending();
                break;
            case 2:
                sort = Sort.by("lostLikeCount").descending();
                break;
            case 3:
                sort = Sort.by("lostCommentCount").descending();
                break;
            case 4:
                sort = Sort.by("lostViewCount").descending();
                break;
            default:
                sort = Sort.by("lostCode").descending();
                break;
        }
        log.info("orderBy" + orderBy);

        Pageable pageable = PageRequest.of(page - 1, 15, sort);
        Page<Lost> result = lostService.lostAll(pageable);
        Paging paging = new Paging();
        paging.setLostList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        paging.setGetNumber(result.getNumber());
        paging.setHasNext(result.hasNext());
        paging.setHasPrev(result.hasPrevious());
        paging.setFirst(result.isFirst());

        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    //분실게시판 검색 GET - http://localhost:8080/api/community/lost/search
    @GetMapping("/community/lost/search")
    public ResponseEntity<Paging> searchCommon(
            @RequestParam(name = "page", defaultValue = "1") int page,@RequestParam(name = "orderBy", defaultValue = "1") int orderBy, @RequestParam String searchKeyword, @RequestParam(name = "searchType", defaultValue = "1") int searchType) {

        try{
        Page<Lost> result = null;
        Pageable pageable = PageRequest.of(page - 1, 15);
        Sort sort;

        switch (orderBy) {
            case 1:
                sort = Sort.by("lostCode").descending();
                break;
            case 2:
                sort = Sort.by("lostLikeCount").descending();
                break;
            case 3:
                sort = Sort.by("lostCommentCount").descending();
                break;
            case 4:
                sort = Sort.by("lostViewCount").descending();
                break;
            default:
                sort = Sort.by("lostCode").descending();
                break;
        }


        switch (searchType){
            case 1:
                result = lostService.searchKeywordAll(searchKeyword,pageable);
                break;
            case 2:
                result = lostService.searchTitle(searchKeyword,pageable);
                break;
            case 3:
                result = lostService.searchDesc(searchKeyword,pageable);
                break;
            default:
                result= null;
                break;
        }

        Paging paging = new Paging();
        paging.setLostList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        paging.setGetNumber(result.getNumber());
        paging.setHasNext(result.hasNext());
        paging.setHasPrev(result.hasPrevious());
        paging.setFirst(result.isFirst());

        return ResponseEntity.status(HttpStatus.OK).body(paging);
       } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //일반게시판 추가 POST - http://localhost:8080/api/community/lost
    @PostMapping("/community/lost")
    public ResponseEntity<Lost> createlost(BoardDTO dto) {
        log.info("lost : " + dto.getTitle());
        log.info("lost : " + dto.toString());

        String userId = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(userId);

        Lost lost = Lost.builder()
                .lostCode(dto.getBoardCode())
                .lostDate(dto.getDate())
                .lostTitle(dto.getTitle())
                .lostDesc(dto.getDesc())
                .member(member)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(lostService.create(lost));
    }


    //일반게시판 수정 PUT - http://localhost:8080/api/community/lost
    @PutMapping("/community/lost")
    public ResponseEntity<Lost> updatelost(@RequestBody BoardDTO dto) {
        log.info("Lost : " + dto.getToken());
        log.info("Lost : " + dto.toString());

        String id = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(id);
        Lost lostPost = lostService.showlost(dto.getBoardCode());
        Lost lost = Lost.builder()
                .lostCode(dto.getBoardCode())
                .lostTitle(dto.getTitle())
                .lostDesc(dto.getDesc())
                .lostDate(lostPost.getLostDate())
                .member(member)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(lostService.update(lost));
    }

    //일반게시판 삭제 DELETE - http://localhost:8080/api/community/1
    @DeleteMapping("/community/lost/{id}")
    public ResponseEntity<Lost> deletelost(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(lostService.delete(id));
    }

    //일반게시판 한 개 조회 GET - http://localhost:8080/api/lost/1
    //조회할 때마다 조회수 추가
    @GetMapping("/community/lost/{lostCode}")
    public ResponseEntity<Lost> showLost(@PathVariable int lostCode) {
        log.info("매핑 됐어??" + lostCode);
        Lost lost = lostService.showlost(lostCode);
        lost.setLostViewCount(lost.getLostViewCount() + 1);
        Lost updateLost = lostService.update(lost);

        return ResponseEntity.status(HttpStatus.OK).body(updateLost);
    }

    //일반게시판 좋아요 추가 POST - http://localhost:8080/api/community/like
    //중복 처리
    @PostMapping("/community/lost/like")
    public ResponseEntity<LostLike> createLostLike(@RequestBody LostLikeDTO lostLikeDTO) {
        String userId = tokenProvider.validateAndGetUserId(lostLikeDTO.getToken());
        Member member = memberService.findByIdUser(userId);
        log.info("member->" + member);

        LostLike target = lostLikeService.likesBymemberAndCommunity(userId, lostLikeDTO.getPostCode());

        if (target == null) {
            Lost lost = lostService.updateLostLike(lostLikeDTO.getPostCode());
            LostLike lostLike = LostLike.builder()
                    .lost(lost)
                    .member(member)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(lostLikeService.create(lostLike));
        } else {
            if (target.getMember().getId().equals(userId) && target.getLost().getLostCode() == lostLikeDTO.getPostCode()) {
                Lost lost = lostService.deleteLostLike(lostLikeDTO.getPostCode());
                LostLike lostLike = lostLikeService.delete(target.getLostLikeCode());

                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}



