package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityLikeService;
import com.kh.Freepets.service.board.community.CommunityService;
import com.kh.Freepets.service.file.FileInputHandler;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin(origins = {"*"},maxAge = 6000)
@RestController
@RequestMapping("/api/*")
public class CommunityController {
    @Autowired
    private CommunityService commonService;
    @Autowired
    private CommunityLikeService commonLikeService;
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadPath;
    @Autowired
    private FileInputHandler handler;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberService memberService;

    //일반게시판 전체 조회 GET - http://localhost:8080/api/community
    //페이징 처리
    @GetMapping("/community")
    public ResponseEntity<List<Community>> commonList(@RequestParam(name = "page", defaultValue = "1") int page) {
        //말머리는...
        //기본정렬
        Sort sort = Sort.by("commonCode").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Community> result = commonService.commonAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    //일반게시판 추가 POST - http://localhost:8080/api/community
    @PostMapping("/community")
    public ResponseEntity<Community> createCommon(BoardDTO dto) {
        log.info("community : " + dto.getTitle());
        log.info("community : " +  dto.toString());
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
        log.info("community : " +  dto.toString());

        String id = tokenProvider.validateAndGetUserId(dto.getToken());
        Member member = memberService.findByIdUser(id);
        Community vo = Community.builder()
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
    public ResponseEntity<CommunityLike> createCommonLike(@RequestBody CommunityLike commonLike) {

        CommunityLike target = commonLikeService.duplicatedLike(commonLike.getCommunity().getMember().getId(), commonLike.getCommunity().getCommonCode());
        if (target == null) {
            commonService.increaseCommonLikes(commonLike.getCommunity().getCommonCode());
            return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.create(commonLike));
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    //일반게시판 좋아요 삭제 DELETE - http://localhost:8080/api/community/like/1
    //추후 기능 부가 및 수정 필요
    @DeleteMapping("/community/like/{commonLikeCode}")
    public ResponseEntity<CommunityLike> deleteCommonLike(@PathVariable int commonLikeCode) {
        CommunityLike communityLike = commonLikeService.showCommonLike(commonLikeCode);
        CommunityLike target = commonLikeService.duplicatedLike(communityLike.getMember().getId(), communityLike.getCommunity().getCommonCode());
        if (target != null) {
            commonService.decreaseCommonLikes(communityLike.getCommunity().getCommonCode());
            return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.delete(commonLikeCode));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    //일반게시판 조회순 정렬 GET - http://localhost:8080/api/community/sortviews
    @GetMapping("/community/sortviews")
    public ResponseEntity<List<Community>> sortCommonViews(@RequestParam(name = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by("commonViewCount").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Community> result = commonService.commonAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    //일반게시판 좋아요순 정렬 GET - http://localhost:8080/api/community/sortlikes
    @GetMapping("/community/sortlikes")
    public ResponseEntity<List<Community>> sortCommonLikes(@RequestParam(name = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by("commonLikeCount").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Community> result = commonService.commonAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    //일반게시판 댓글순 정렬 GET - http://localhost:8080/api/community/sortcomments
    @GetMapping("/community/sortcomments")
    public ResponseEntity<List<Community>> sortCommonComments(@RequestParam(name = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by("commonCommentCount").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Community> result = commonService.commonAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }
}
