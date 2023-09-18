package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.LostComment;
import com.kh.Freepets.service.board.community.LostCommentService;
import com.kh.Freepets.service.board.community.LostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class LostCommentController {

    @Autowired
    private LostCommentService lostcomment;

    @Autowired
    private LostService lostservice;


    // 게시물 1개에 따른 댓글 전체 보기 GET - http://localhost:8080/api/lost/1/comment
     //SELECT * FROM LOST_COMMENT WHERE LOST_CODE = ?
     @GetMapping("/lost/{id}/comment")
     private ResponseEntity<List<LostComment>> lostCommentList(@PathVariable int lostCode){
         return ResponseEntity.status(HttpStatus.OK).body(lostcomment.findByLostCode(lostCode));
     }


    //분실신고 게시글 댓글 추가하기 POST - http://localhost:8080/api/lost/comment
    // 동시에 댓글 갯수 추가(LostDAO - LostService 작성)
    @PostMapping("/lost/comment")
    private ResponseEntity<LostComment> createLostComment(@RequestBody LostComment vo){
        lostservice.updateLostCommentCount(vo.getLost().getLostCode());
        return ResponseEntity.status(HttpStatus.OK).body(lostcomment.create(vo));
    }

    //분실신고 게시글 댓글 수정하기 PUT -http://localhost:8080/api/lost/comment
    @PutMapping("/lost/comment")
    private ResponseEntity <LostComment> updateLostComment(@RequestBody LostComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(lostcomment.update(vo));
    }

    //분실신고 게시글 댓글 삭제하기 DELETE - http://localhost:8080/api/lost/comment/{lCommentCode}
    // 동시에 댓글 갯수 삭제 (LostDAO - LostService 작성)
    @DeleteMapping("/lost/comment/{id}")
    private ResponseEntity<LostComment> deleteLostComment(int lCommentCode){
        LostComment target = lostcomment.showLostComment(lCommentCode);

         lostservice.deleteLostCommentCount(target.getLost().getLostCode());
        return ResponseEntity.status(HttpStatus.OK).body(lostcomment.delete(lCommentCode));
    }

    // 댓글에 사진 첨부하기
    // 댓글에 사진 수정하기
    // 댓글에 사진 삭제하기


}
