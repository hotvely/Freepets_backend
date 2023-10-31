package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.community.LostComment;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityCommentService;
import com.kh.Freepets.service.board.community.CommunityService;
import com.kh.Freepets.service.board.community.LostCommentService;
import com.kh.Freepets.service.member.MemberService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin
public class LostCommentController {
    @Autowired
    private LostCommentService lostCommentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TokenProvider token;

    //일반 게시글 한 개에 따른 댓글 전체 조회 GET - http://localhost:8080/api/community/lost/1/comment
    @GetMapping("/community/lost/{commonCode}/comments")
    private ResponseEntity<List<LostComment>> lostCommentList(@PathVariable int lostCode){
        log.info("여기서 나오냐고ㅜㅜ");
        return ResponseEntity.status(HttpStatus.OK).body(lostCommentService.lostCommentAll(lostCode));
    }

    @GetMapping("/community/lost/comment/{commonCommentCode}")
    private ResponseEntity <LostComment> getLostComment(@PathVariable int lostCommentCode){
        return ResponseEntity.ok().body(lostCommentService.showLostComment(lostCommentCode));
    }

    // 부모 댓글에 따른 자식 댓글 보기 GET - http://localhost:8080/api/community/lost/comment/{commonCommentCode}
    @GetMapping("/community/lost/comment/{commonCommentCodeSuper}")
    private ResponseEntity<List<LostComment>> showLostComment(@PathVariable int lostCommentCodeSuper) {
        return ResponseEntity.status(HttpStatus.OK).body(lostCommentService.lostReCommentAll(lostCommentCodeSuper));
    }

    //일반 게시글 댓글 추가 POST - http://localhost:8080/api/community/lost/comment
    @PostMapping("/community/lost/comment")
    public ResponseEntity<LostComment> createLostComment(@RequestBody CommentDTO commentDTO){
        if(commentDTO.getBoardName().equals("lost")){
            String userId = token.validateAndGetUserId(commentDTO.getToken());
            Member member = memberService.findByIdUser(userId);

            Lost lost = Lost.builder()
                    .lostCode(commentDTO.getPostCode())
                    .build();
            LostComment lostComment = LostComment.builder()
                    .lost(lost)
                    .lostCommentCode(commentDTO.getCommentCode())
                    .lostCommentDesc(commentDTO.getCommentDesc())
                    .lostCommentCodeSuper(commentDTO.getParentCommentCode())
                    .member(member)
                    .build();
            lostCommentService.create(lostComment);

            return ResponseEntity.status(HttpStatus.OK).body(lostComment);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //일반 게시글 댓글 수정 UPDATE - http://localhost:8080/api/community/comment
    @PutMapping("/community/lost/comment")
    public ResponseEntity<LostComment> updateLostComment(@RequestBody CommentDTO commentDTO){
        LostComment lostComment = LostComment.builder()
                .lostCommentDesc(commentDTO.getCommentDesc())
                .lostCommentDate(new Date())
                .build();
        lostCommentService.update(lostComment);

        return ResponseEntity.status(HttpStatus.OK).body(lostComment);
    }

    //일반 게시글 댓글 삭제 DELETE - http://localhost:8080/api/community/comment/1
    @DeleteMapping("/community/lost/comment/{commonCommentCode}")
    public ResponseEntity<LostComment>deleteLostComment(@PathVariable int lostCommentCode){
        return ResponseEntity.status(HttpStatus.OK).body(lostCommentService.delete(lostCommentCode));
    }


}
