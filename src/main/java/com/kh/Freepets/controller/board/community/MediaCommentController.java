package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.MediaComment;
import com.kh.Freepets.service.board.community.MediaCommentService;
import com.kh.Freepets.service.board.community.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class MediaCommentController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaCommentService mediaCommentService;

    //미디어 게시글 한 개에 따른 댓글 전체 조회 GET - http://localhost:8000/api/community/1/comment
    @GetMapping("/media/{mediaCode}/comment")
    private ResponseEntity<List<MediaComment>> mediaCommentList(@PathVariable int mediaCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.findByMediaCode(mediaCode));
    }
    //미디어 게시글 댓글 추가 POST - http://localhost:8000/api/community/comment
    @PostMapping("/media/comment")
    public ResponseEntity<MediaComment> createMediaComment(@RequestBody MediaComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.create(vo));
    }

    //미디어 게시글 댓글 수정 UPDATE - http://localhost:8000/api/community/comment
    @PutMapping("/media/comment")
    public ResponseEntity<MediaComment> updateMediaComment(@RequestBody MediaComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.update(vo));
    }
    //미디어 게시글 댓글 삭제 DELETE - http://localhost:8000/api/community/comment/1
    @DeleteMapping("/media/{commonCommentCode}")
    public ResponseEntity<MediaComment>deleteMediaComment(@PathVariable int mediaCommentCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.delete(mediaCommentCode));
    }

    //추후 부가 기능 추가 및 수정 예정..아마도..?
}
