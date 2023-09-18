package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.service.board.community.CommunityLikeService;
import com.kh.Freepets.service.board.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class CommunityController {
    @Autowired
    private CommunityService commonService;
    @Autowired
    private CommunityLikeService commonLikeService;

    //일반게시판 전체 조회 GET - http://localhost:8000/api/community
    @GetMapping("/community")
    public ResponseEntity<List<Community>> commonList(){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.commonAll());
    }
    //일반게시판 추가 POST - http://localhost:8000/api/community
    @PostMapping("/community")
    public ResponseEntity<Community> createCommon(@RequestBody Community vo){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.create(vo));
    }
    //일반게시판 수정 PUT - http://localhost:8000/api/community
    @PutMapping("/community")
    public ResponseEntity<Community> updateCommon(@RequestBody Community vo){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.update(vo));
    }
    //일반게시판 삭제 DELETE - http://localhost:8000/api/community/1
    @DeleteMapping("/community/{commonCode}")
    public ResponseEntity<Community>deleteCommon(@PathVariable int commonCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.delete(commonCode));
    }

    //일반게시판 한 개 조회 GET - http://localhost:8000/api/community/1
    @GetMapping("/community/{commonCode}")
    public ResponseEntity <Community>showCommon(@PathVariable int commonCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.showCommon(commonCode));
    }

    //일반게시판 좋아요 추가 POST - http://localhost:8000/api/community/like/1
    //추후 기능 부가 및 수정 필요
    @PostMapping("/community/like")
    public ResponseEntity <CommunityLike> createCommonLike(@RequestBody CommunityLike commonLike){
        return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.create(commonLike));
    }
    //일반게시판 좋아요 삭제 DELETE - http://localhost:8000/api/community/like/1
    //추후 기능 부가 및 수정 필요
    @DeleteMapping("/community/like/{commonLikeCode}")
    public ResponseEntity<CommunityLike>deleteCommonLike(@PathVariable int commonLikeCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.delete(commonLikeCode));
    }
    //일반게시판 댓글 수에 따른 게시글 정렬
    //일반게시판 좋아요 수에 따른 게시글 정렬
}
