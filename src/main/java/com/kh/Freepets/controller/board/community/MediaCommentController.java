package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.MediaComment;
import com.kh.Freepets.service.board.community.MediaCommentService;
import com.kh.Freepets.service.board.community.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class MediaCommentController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaCommentService mediaCommentService;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    //미디어 게시글 한 개에 따른 댓글 전체 조회 GET - http://localhost:8080/api/media/1/comment
    @GetMapping("/media/{mediaCode}/comment")
    private ResponseEntity<List<MediaComment>> mediaCommentList(@PathVariable int mediaCode){
        log.info("군밤군밤?");
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.findByMediaCode(mediaCode));
    }
    //미디어 게시글 댓글 추가 POST - http://localhost:8080/api/media/comment
    @PostMapping("/media/comment")
    public ResponseEntity<MediaComment> createMediaComment(String desc, MultipartFile file){
//        log.info("군밤이 여기까지 왔늬?");
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
        MediaComment vo = new MediaComment();
        vo.setMediaCommentAddFileUrl(uuid + "_" + realFile);
        vo.setMediaCommentDesc(desc);

        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.create(vo));
    }

    //미디어 게시글 댓글 수정 UPDATE - http://localhost:8080/api/media/comment
    @PutMapping("/media/comment")
    public ResponseEntity<MediaComment> updateMediaComment(@RequestBody MediaComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.update(vo));
    }
    //미디어 게시글 댓글 삭제 DELETE - http://localhost:8080/api/media/comment/1
    @DeleteMapping("/media/comment/{mediaCommentCode}")
    public ResponseEntity<MediaComment>deleteMediaComment(@PathVariable int mediaCommentCode){
        log.info("삭제 짆행훼");
        return ResponseEntity.status(HttpStatus.OK).body(mediaCommentService.delete(mediaCommentCode));
    }

    //추후 부가 기능 추가 및 수정 예정..아마도..?
}
