package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Media;
import com.kh.Freepets.domain.board.community.MediaLike;
import com.kh.Freepets.service.board.community.MediaLikeService;
import com.kh.Freepets.service.board.community.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
public class MediaController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaLikeService mediaLikeService;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    //미디어 전체 조회 GET - http://localhost:8080/api/media
    @GetMapping("/media")
    public ResponseEntity<List<Media>> mediaList(@RequestParam (name="page", defaultValue="1") int page){
        // 말머리 처리는 어떻게 해야하나.....
        // 기본정렬
        Sort sort = Sort.by("mediaCode").descending();
        Pageable pageable = PageRequest.of(page-1,15,sort);
        Page<Media> result =mediaService.mediaAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }
    //미디어 추가 POST - http://localhost:8080/api/media
    @PostMapping("/media")
    public ResponseEntity<Media> createMedia(MultipartFile file, String title, String desc){
        //유튜브 첨부하는 거 나중에 추가
        //파일 업로드
        String originalFile = file.getOriginalFilename();
        String realFile = originalFile.substring(originalFile.indexOf("\\")+1); //저장할 파일 이름
        String uuid = UUID.randomUUID().toString();

        String saveFile = uploadPath + File.separator + uuid + "_" + realFile;
        Path pathFile = Paths.get(saveFile);
        try{
        file.transferTo(pathFile);
        }catch (IOException e){
        throw new RuntimeException(e);
        }

        Media vo = new Media();
        vo.setMediaPhoto(uuid + "_" + realFile);    //썸네일
        vo.setMediaAddFileUrl(uuid + "_" + realFile);   //게시글에 올릴 파일들
        vo.setMediaTitle(title);
        vo.setMediaDesc(desc);

        //멤버가 필요할까...?
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.create(vo));
    }
    //미디어 수정 PUT - http://localhost:8080/api/media
    @PutMapping("/media")
    public ResponseEntity<Media> updateMedia(@RequestBody Media vo){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.update(vo));
    }
    //미디어 삭제 DELETE - http://localhost:8080/api/media/1
    @DeleteMapping("/media/{mediaCode}")
    public ResponseEntity<Media>deleteMedia(@PathVariable int mediaCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.delete(mediaCode));
    }
    //미디어 한 개 조회 GET - http://localhost:8080/api/media/1
    @GetMapping("/media/{mediaCode}")
    public ResponseEntity <Media>showMedia(@PathVariable int mediaCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaService.showMedia(mediaCode));
    }
    //미디어 좋아요 추가 POST - http://localhost:8080/api/media/like/1
    //추후 기능 부가 및 수정 필요
    @PostMapping("/media/like")
    public ResponseEntity <MediaLike> createMediaLike(@RequestBody MediaLike mediaLike){
        return ResponseEntity.status(HttpStatus.OK).body(mediaLikeService.create(mediaLike));
    }
    //미디어 좋아요 삭제 DELETE - http://localhost:8080/api/media/like/1
    //추후 기능 부가 및 수정 필요
    @DeleteMapping("/media/like/{mediaLikeCode}")
    public ResponseEntity<MediaLike>deleteMediaLike(@PathVariable int mediaLikeCode){
        return ResponseEntity.status(HttpStatus.OK).body(mediaLikeService.delete(mediaLikeCode));
    }
    //미디어 댓글 수에 따른 게시글 정렬
    //미디어 좋아요 수에 따른 게시글 정렬
}
