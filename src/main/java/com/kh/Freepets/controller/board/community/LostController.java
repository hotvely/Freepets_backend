package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.community.Lost;
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

    //일반게시판 검색 GET - http://localhost:8080/api/community/search
//    @GetMapping("/community/search")
//    public ResponseEntity<Paging> searchCommon(
//            @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam String searchKeyword, @RequestParam(name = "searchType", defaultValue = "1") int searchType) {
//
//        Pageable pageable = PageRequest.of(page - 1, 15);
//
//        Page<Community> result = commonService.searchKeyword(searchKeyword, searchType, pageable);
//        Paging paging = new Paging();
//        paging.setCommunityList(result.getContent());
//        paging.setTotalCount(result.getTotalElements());
//        paging.setTotalPages(result.getTotalPages());
//        paging.setGetNumber(result.getNumber());
//        paging.setHasNext(result.hasNext());
//        paging.setHasPrev(result.hasPrevious());
//        paging.setFirst(result.isFirst());
//
//        return ResponseEntity.status(HttpStatus.OK).body(paging);
//    }

    //일반게시판 추가 POST - http://localhost:8080/api/community/lost
    @PostMapping("/community/lost")
    public ResponseEntity<Lost> createlost(BoardDTO dto) {
        log.info("lost : " + dto.getTitle());
        log.info("lost : " + dto.toString());

        String userId = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(userId);

        Lost lost = Lost.builder()
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
        Lost lost = Lost.builder()
                .lostCode(dto.getBoardCode())
                .lostTitle(dto.getTitle())
                .lostDesc(dto.getDesc())
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
        Lost lost = lostService.showlost(lostCode);
        lost.setLostViewCount(lost.getLostViewCount() + 1);
        Lost updateLost = lostService.update(lost);

        return ResponseEntity.status(HttpStatus.OK).body(updateLost);
    }

    //일반게시판 좋아요 추가 POST - http://localhost:8080/api/community/lost/like
    //중복 처리
//    @PostMapping("/community/lost/like")
//    public ResponseEntity<Lost> createLostLike(@RequestBody LostLike lostLike) {
//        BoardDTO boardDTO = new BoardDTO();
//        String userId = tokenProvider.validateAndGetUserId(boardDTO.getToken());
//        Member member = memberService.findByIdUser(userId);
//        log.info("member->" + member);
//
//        LostLike target = lostLikeService.likesBymemberAndCommunity(lostLike.getMember().getId(), lostLike.getLost().getLostCode());
//
//        if (target == null) {
//            Lost lost = lostService.updateLostLike(lostLike.getLost().getLostCode());
//            LostLike lostLike1 = LostLike.builder()
//                    .lost(lost)
//                    .member(member)
//                    .build();
//            return ResponseEntity.status(HttpStatus.OK).body(lostLikeService.create(lostLike));
//        } else {
//            if (target.getMember().getId().equals(userId) && target.getCommunity().getCommonCode() == boardDTO.getBoardCode()) {
//                Community community = commonService.deleteCommonLike(boardDTO.getBoardCode());
//                CommunityLike communityLike = commonLikeService.delete(target.getCommonLikeCode());
//                return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.delete(commonLike.getCommonLikeCode()));
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
//
//    }
}


