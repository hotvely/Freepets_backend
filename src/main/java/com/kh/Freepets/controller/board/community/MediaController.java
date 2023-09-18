package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Media;
import com.kh.Freepets.domain.board.community.MediaLike;
import com.kh.Freepets.service.board.community.MediaLikeService;
import com.kh.Freepets.service.board.community.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class MediaController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaLikeService mediaLikeService;

    //미디어 전체 조회 GET - http://localhost:8000/api/media
    @GetMapping("/media")
    public ResponseEntity<List<Media>> mediaList(){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.mediaAll());
    }
    //미디어 추가 POST - http://localhost:8000/api/media
    @PostMapping("/media")
    public ResponseEntity<Media> createMedia(@RequestBody Media vo){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.create(vo));
    }
    //미디어 수정 PUT - http://localhost:8000/api/media
    @PutMapping("/media")
    public ResponseEntity<Media> updateMedia(@RequestBody Media vo){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.update(vo));
    }
    //미디어 삭제 DELETE - http://localhost:8000/api/media/1
    @DeleteMapping("/media/{mediaCode}")
    public ResponseEntity<Media>deleteMedia(@PathVariable int mediaCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.delete(mediaCode));
    }
    //미디어 한 개 조회 GET - http://localhost:8000/api/media/1
    @GetMapping("/media/{mediaCode}")
    public ResponseEntity <Media>showMedia(@PathVariable int mediaCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.showMedia(mediaCode));
    }
    //미디어 좋아요 추가 POST - http://localhost:8000/api/media/like/1
    //추후 기능 부가 및 수정 필요
    @PostMapping("/media/like")
    public ResponseEntity <MediaLike> createMediaLike(@RequestBody MediaLike mediaLike){
        return ResponseEntity.status(HttpStatus.OK).body(mediaLikeService.create(mediaLike));
    }
    //미디어 좋아요 삭제 DELETE - http://localhost:8000/api/media/like/1
    //추후 기능 부가 및 수정 필요
    @DeleteMapping("/media/like/{mediaLikeCode}")
    public ResponseEntity<MediaLike>deleteMediaLike(@PathVariable int mediaLikeCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaLikeService.delete(mediaLikeCode));
    }
    //미디어 댓글 수에 따른 게시글 정렬
    //미디어 좋아요 수에 따른 게시글 정렬
}
