package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.community.LostLike;
import com.kh.Freepets.service.board.community.LostLikeService;
import com.kh.Freepets.service.board.community.LostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
public class LostController {

     @Autowired
     private LostService service;

     @Autowired
     private LostLikeService lostlikeservice;

     // 분실신고 게시글 전체보기
     @GetMapping("/lost")
    public ResponseEntity<List<Lost>>showAll(){
         return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
     }

    // 분실신고 게시글 상세보기
    @GetMapping("/lost/{lostCode}")
    public ResponseEntity <Lost>showLost(@PathVariable int lostCode){
        return ResponseEntity.status(HttpStatus.OK).body(service.show(lostCode));
    }

    // 분실신고 게시글 추가하기
    @PostMapping("/lost")
    public ResponseEntity <Lost> createLost(@RequestBody Lost lost){
         return ResponseEntity.status(HttpStatus.OK).body(service.create(lost));
    }

    // 분실신고 게시글 수정하기
    @PutMapping("/lost")
    public ResponseEntity<Lost> updateLost(@RequestBody Lost lost){
         return ResponseEntity.status(HttpStatus.OK).body(service.update(lost));
    }

    // 분실신고 게시글 삭제하기
    @DeleteMapping("/lost/{id}")
    public ResponseEntity<Lost>deleteLost(@PathVariable int lostCode){
         return ResponseEntity.status(HttpStatus.OK).body(service.delete(lostCode));
    }

    // 분실 신고 좋아요 추가  POST - http://localhost:8080/api/lost/like
    // 동시에 좋아요 갯수 추가 (LostDAO에 쿼리문 작성 )
    // 동시에 좋아요 갯수 중복 처리 (LostLikeDAO에 쿼리문 작성 )
    @PostMapping("/lost/like")
    public ResponseEntity<LostLike> createLostLike(@RequestBody LostLike lostlike){
        LostLike target = lostlikeservice.noDoubleLike(lostlike.getLost().getMember().getId(), lostlike.getLost().getLostCode());


         if(target==null){
             service.updatelike(lostlike.getLost().getLostCode());
             return ResponseEntity.status(HttpStatus.OK).body(lostlikeservice.create(lostlike));
         }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    // 분실 신고 좋아요 삭제 DELETE - http://localhost:8080/api/lost/like/{lostLikeCode}
    // 좋아요 갯수 차감 (LostDAO에 쿼리문 작성 )
    // 동시에 좋아요 갯수 중복 처리 (LostLikeDAO에 쿼리문 작성 )
    @DeleteMapping("/lost/like/{id}")
    public ResponseEntity<LostLike> deleteLostLike(@PathVariable int lostLikeCode){

         LostLike lostlike = lostlikeservice.showLostLike(lostLikeCode);
         LostLike target = lostlikeservice.noDoubleLike(lostlike.getLost().getMember().getId(), lostlike.getLost().getLostCode());

         if(target!=null){
             service.deletelike(lostlike.getLost().getLostCode());
             return ResponseEntity.status(HttpStatus.OK).body(lostlikeservice.delete(lostLikeCode));
         }
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

     // 댓글수 별 게시글 정렬 Get - http://localhost:8080/api/lost/sortcomment
     @GetMapping("/lost/sortcomment")
    public ResponseEntity <List<Lost>> sortCommentCount(){
        return  ResponseEntity.status(HttpStatus.OK).body(service.sortCommentCount());
     }

     // 좋아요수 별 게시글 정렬 Get - http://localhost:8080/api/lost/sortlike
    @GetMapping("/lost/sortlike")
    public ResponseEntity <List<Lost>> sortLostLike(){
         return ResponseEntity.status(HttpStatus.OK).body(service.sortLostLike());
    }


}
