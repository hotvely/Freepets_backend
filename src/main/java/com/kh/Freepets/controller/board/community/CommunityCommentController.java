package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.service.board.community.CommunityCommentService;
import com.kh.Freepets.service.board.community.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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
public class CommunityCommentController {
    @Autowired
    private CommunityService commonService;
    @Autowired
    private CommunityCommentService commonCommentService;
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadPath;

    //일반 게시글 한 개에 따른 댓글 전체 조회 GET - http://localhost:8080/api/community/1/comment
    @GetMapping("/community/{commonCode}/comment")
    private ResponseEntity<List<CommunityComment>> commonCommentList(@PathVariable int commonCode){
        log.info("안오냑오");
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.findByCommonCode(commonCode));
    }

    //일반 게시글 댓글 추가 POST - http://localhost:8080/api/community/comment
    @PostMapping("/community/comment")
    public ResponseEntity<CommunityComment> createCommonComment(@RequestBody CommunityComment vo){
//        String desc, MultipartFile file
//        String originalFile = file.getOriginalFilename();
//        String realFile = originalFile.substring(originalFile.indexOf("\\")+1); //저장할 파일 이름
//        String uuid = UUID.randomUUID().toString();
//
//        String saveFile = uploadPath + File.separator + uuid + "_" + realFile;
//        Path pathFile = Paths.get(saveFile);
//        try{
//            file.transferTo(pathFile);
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//
//        CommunityComment vo = new CommunityComment();
//        vo.setCommonCommentAddFileUrl(uuid + "_" + realFile);
//        vo.setCommonCommentDesc(desc);
//        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.create(vo));
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.create(vo));
    }

    //일반 게시글 댓글 수정 UPDATE - http://localhost:8080/api/community/comment
    @PutMapping("/community/comment")
    public ResponseEntity<CommunityComment> updateCommonComment(@RequestBody CommunityComment vo){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.update(vo));
    }

    //일반 게시글 댓글 삭제 DELETE - http://localhost:8080/api/community/comment/1
    @DeleteMapping("/community/comment/{commonCommentCode}")
    public ResponseEntity<CommunityComment>deleteCommonComment(@PathVariable int commonCommentCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.delete(commonCommentCode));
    }


}
