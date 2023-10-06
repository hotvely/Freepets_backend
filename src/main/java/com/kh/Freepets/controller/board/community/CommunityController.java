package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.service.board.community.CommunityLikeService;
import com.kh.Freepets.service.board.community.CommunityService;
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
public class CommunityController {
    @Autowired
    private CommunityService commonService;
    @Autowired
    private CommunityLikeService commonLikeService;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    //일반게시판 전체 조회 GET - http://localhost:8080/api/community
    //페이징 처리
    @GetMapping("/community")
    public ResponseEntity<List<Community>> commonList(@RequestParam(name="page", defaultValue = "1") int page){
        //말머리는...
        //기본정렬
        Sort sort = Sort.by("commonCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Community> result = commonService.commonAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }
    //일반게시판 추가 POST - http://localhost:8080/api/community
    @PostMapping("/community")
    public ResponseEntity<Community> createCommon(MultipartFile file, String title, String desc){
        //유튜브 첨부 찾아보기
        //파일 업로드
        String originalFile = file.getOriginalFilename();
        String realFile = originalFile.substring(originalFile.indexOf("\\")+1);
        String uuid = UUID.randomUUID().toString();
        String saveFile = uploadPath + File.separator + uuid + "_" + realFile;
        Path pathFile = Paths.get(saveFile);
        try{
            file.transferTo(pathFile);
        }catch (IOException e ) {
            throw new RuntimeException(e);
        }

        Community vo = new Community();
        vo.setCommonAddFileUrl(uuid + "_" + realFile);
        vo.setCommonTitle(title);
        vo.setCommonDesc(desc);
        return ResponseEntity.status(HttpStatus.OK).body(commonService.create(vo));
    }
    //일반게시판 수정 PUT - http://localhost:8080/api/community
    @PutMapping("/community")
    public ResponseEntity<Community> updateCommon(@RequestBody Community vo){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.update(vo));
    }
    //일반게시판 삭제 DELETE - http://localhost:8080/api/community/1
    @DeleteMapping("/community/{id}")
    public ResponseEntity<Community> deleteCommon(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.delete(id));
    }

    //일반게시판 한 개 조회 GET - http://localhost:8080/api/community/1
    @GetMapping("/community/{commonCode}")
    public ResponseEntity <Community> showCommon(@PathVariable int commonCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonService.showCommon(commonCode));
    }

    //일반게시판 좋아요 추가 POST - http://localhost:8080/api/community/like/1
    //추후 기능 부가 및 수정 필요
    @PostMapping("/community/like")
    public ResponseEntity <CommunityLike> createCommonLike(@RequestBody CommunityLike commonLike){
        return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.create(commonLike));
    }
    //일반게시판 좋아요 삭제 DELETE - http://localhost:8080/api/community/like/1
    //추후 기능 부가 및 수정 필요
    @DeleteMapping("/community/like/{commonLikeCode}")
    public ResponseEntity<CommunityLike>deleteCommonLike(@PathVariable int commonLikeCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonLikeService.delete(commonLikeCode));
    }
    //일반게시판 댓글 수에 따른 게시글 정렬
    //일반게시판 좋아요 수에 따른 게시글 정렬
}
