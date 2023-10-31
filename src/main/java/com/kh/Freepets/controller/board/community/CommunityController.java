package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.domain.board.community.CommunityLikeDTO;
import com.kh.Freepets.domain.board.community.QCommunity;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.domain.util.Paging;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityLikeService;
import com.kh.Freepets.service.board.community.CommunityService;
import com.kh.Freepets.service.file.FileInputHandler;
import com.kh.Freepets.service.member.MemberService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class CommunityController {
    @Autowired
    private CommunityService commonService;
    @Autowired
    private CommunityLikeService commonLikeService;
    @Autowired
    private FileInputHandler handler;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JPAQueryFactory queryFactory;
    private QCommunity qCommunity = QCommunity.community;

    //일반게시판 전체 조회 GET - http://localhost:8080/api/community
    //페이징 처리
    @GetMapping("/community")
    public ResponseEntity<Paging> commonList(
            @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "orderBy", defaultValue = "1") int orderBy) {
        Sort sort;

        switch (orderBy) {
            case 1:
                sort = Sort.by("commonCode").descending();
                break;
            case 2:
                sort = Sort.by("commonLikeCount").descending();
                break;
            case 3:
                sort = Sort.by("commonCommentCount").descending();
                break;
            case 4:
                sort = Sort.by("commonViewCount").descending();
                break;
            default:
                sort = Sort.by("commonCode").descending();
                break;
        }
        log.info("orderBy" + orderBy);

        Pageable pageable = PageRequest.of(page - 1, 15, sort);
        Page<Community> result = commonService.commonAll(pageable);
        Paging paging = new Paging();
        paging.setCommunityList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        paging.setGetNumber(result.getNumber());
        paging.setHasNext(result.hasNext());
        paging.setHasPrev(result.hasPrevious());
        paging.setFirst(result.isFirst());

        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    //일반게시판 검색 GET - http://localhost:8080/api/community/search
    @GetMapping("/community/search")
    public ResponseEntity<Paging> searchCommon(
            @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam String searchKeyword, @RequestParam(name = "searchType", defaultValue = "1") int searchType) {
//        log.info("page" + page);
//        log.info("searchKeyword" + searchKeyword);
//        log.info("searchType" + searchType);

//        Sort sort = Sort.by("commonCode").descending();
//        BooleanBuilder builder = new BooleanBuilder();
//        if (orderBy == 2) {
//            BooleanExpression expression = qCommunity.commonLikeCount.eq(orderBy);
//        } else if (orderBy == 3) {
//            BooleanExpression expression = qCommunity.commonCommentCount.eq(orderBy);
//        } else if (orderBy == 4) {
//            BooleanExpression expression = qCommunity.commonViewCount.eq(orderBy);
//        } else {
//            BooleanExpression expression = qCommunity.commonCode.eq(orderBy);
//        }


        Pageable pageable = PageRequest.of(page - 1, 15);

        Page<Community> result = commonService.searchKeyword(searchKeyword, searchType, pageable);
        Paging paging = new Paging();
        paging.setCommunityList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        paging.setGetNumber(result.getNumber());
        paging.setHasNext(result.hasNext());
        paging.setHasPrev(result.hasPrevious());
        paging.setFirst(result.isFirst());

        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    //일반게시판 추가 POST - http://localhost:8080/api/community
    @PostMapping("/community")
    public ResponseEntity<Community> createCommon(BoardDTO dto) {
        log.info("community : " + dto.getTitle());
        log.info("community : " + dto.toString());
        //파일 업로드

        String userId = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(userId);

        Community vo = Community.builder()
                .commonTitle(dto.getTitle())
                .commonDesc(dto.getDesc())
                .member(member)
                .build();
//            vo.setCommonTitle(commonTitle);
//            vo.setCommonDesc(commonDesc);
//            vo.setCommonAddFileUrl(fileName);
//            Member member = new Member();
//            member.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(commonService.create(vo));
    }


    //일반게시판 수정 PUT - http://localhost:8080/api/community
    @PutMapping("/community")
    public ResponseEntity<Community> updateCommon(@RequestBody BoardDTO dto) {
        log.info("community : " + dto.getToken());
        log.info("community : " + dto.toString());

        String id = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(id);
        Community vo = Community.builder()
                .commonCode(dto.getBoardCode())
                .commonTitle(dto.getTitle())
                .commonDesc(dto.getDesc())
                .member(member)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonService.update(vo));
    }

    //일반게시판 삭제 DELETE - http://localhost:8080/api/community/1
    @DeleteMapping("/community/{id}")
    public ResponseEntity<Community> deleteCommon(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(commonService.delete(id));
    }

    //일반게시판 한 개 조회 GET - http://localhost:8080/api/community/1
    //조회할 때마다 조회수 추가
    @GetMapping("/community/{commonCode}")
    public ResponseEntity<Community> showCommon(@PathVariable int commonCode) {
        Community vo = commonService.showCommon(commonCode);
        vo.setCommonViewCount(vo.getCommonViewCount() + 1);
        Community updateVo = commonService.update(vo);

        return ResponseEntity.status(HttpStatus.OK).body(updateVo);
    }

    //일반게시판 좋아요 추가 POST - http://localhost:8080/api/community/like
    //중복 처리
    @PostMapping("/community/like")
    public ResponseEntity<CommunityLike> createCommonLike(@RequestBody CommunityLikeDTO communityLikeDTO) {
        String userId = tokenProvider.validateAndGetUserId(communityLikeDTO.getToken());
        Member member = memberService.findByIdUser(userId);
        log.info("member->" + member);

        CommunityLike target = commonLikeService.likesBymemberAndCommunity(userId, communityLikeDTO.getPostCode());

        if (target == null) {
            Community community = commonService.updateCommonLike(communityLikeDTO.getPostCode());
            CommunityLike communityLike = CommunityLike.builder()
                    .community(community)
                    .member(member)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.create(communityLike));
        } else {
            if (target.getMember().getId().equals(userId) && target.getCommunity().getCommonCode() == communityLikeDTO.getPostCode()) {
                Community community = commonService.deleteCommonLike(communityLikeDTO.getPostCode());
                CommunityLike communityLike = commonLikeService.delete(target.getCommonLikeCode());

                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
    //일반게시판 좋아요 삭제 DELETE - http://localhost:8080/api/community/like/1
    //추후 기능 부가 및 수정 필요
//    @DeleteMapping("/community/like/{commonLikeCode}")
//    public ResponseEntity<CommunityLike> deleteCommonLike(@PathVariable int commonLikeCode) {
//        CommunityLike communityLike = commonLikeService.showCommonLike(commonLikeCode);
//        CommunityLike target = commonLikeService.duplicatedLike(communityLike.getMember().getId(), communityLike.getCommunity().getCommonCode());
//        if (target != null) {
//            commonService.decreaseCommonLikes(communityLike.getCommunity().getCommonCode());
//            return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.delete(commonLikeCode));
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }




