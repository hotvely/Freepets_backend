package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.service.board.community.CommunityCommentService;
import com.kh.Freepets.service.board.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class CommunityCommentController {
    @Autowired
    private CommunityService commonService;
    @Autowired
    private CommunityCommentService commonCommentService;

    //일반 게시글 한 개에 따른 댓글 전체 조회 GET - http://localhost:8000/api/community/1/comment
    @GetMapping("/community/{commonCode}/comment")
    private ResponseEntity<List<CommunityComment>> commonCommentList(@PathVariable int commonCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.findByCommonCode(commonCode));
    }

    //일반 게시글 댓글 추가 POST - http://localhost:8000/api/community/comment
    @PostMapping("/community/comment")
    public ResponseEntity<CommunityComment> createCommonComment(@RequestBody CommunityComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.create(vo));
    }

    //일반 게시글 댓글 수정 UPDATE - http://localhost:8000/api/community/comment
    @PutMapping("/community/comment")
    public ResponseEntity<CommunityComment> updateCommonComment(@RequestBody CommunityComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.update(vo));
    }

    //일반 게시글 댓글 삭제 DELETE - http://localhost:8000/api/community/comment/1
    @DeleteMapping("/community/{commonCommentCode}")
    public ResponseEntity<CommunityComment>deleteCommonComment(@PathVariable int commonCommentCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.delete(commonCommentCode));
    }

    //추후 부가 기능 추가 및 수정 예정..아마도..?
}
