package com.kh.Freepets.controller.board.community;

import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityCommentService;
import com.kh.Freepets.service.board.community.CommunityService;
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
public class CommunityCommentController {
    @Autowired
    private CommunityCommentService commonCommentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TokenProvider token;
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadPath;

    //일반 게시글 한 개에 따른 댓글 전체 조회 GET - http://localhost:8080/api/community/1/comment
    @GetMapping("/community/{commonCode}/comments")
    private ResponseEntity<List<CommunityComment>> commonCommentList(@PathVariable int commonCode){
        log.info("여기서 나오냐고ㅜㅜ");
      return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.commonCommentAll(commonCode));
    }

    @GetMapping("/community/comment/{commonCommentCode}")
    private ResponseEntity <CommunityComment> getCommonComment(@PathVariable int commonCommentCode){
        return ResponseEntity.ok().body(commonCommentService.showCommonComment(commonCommentCode));
    }

    // 부모 댓글에 따른 자식 댓글 보기 GET - http://localhost:8080/api/community/comment/{commonCommentCode}
    @GetMapping("/community/comment/{commonCommentCodeSuper}")
    private ResponseEntity<List<CommunityComment>> showCommonComment(@PathVariable int commonCommentCodeSuper) {
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.commonReCommentAll(commonCommentCodeSuper));
    }



    //일반 게시글 댓글 추가 POST - http://localhost:8080/api/community/comment
    @PostMapping("/community/comment")
    public ResponseEntity<CommunityComment> createCommonComment(@RequestBody CommentDTO commentDTO){
        if(commentDTO.getBoardName().equals("community")){
        String userId = token.validateAndGetUserId(commentDTO.getToken());
        Member member = memberService.findByIdUser(userId);

        Community community = Community.builder()
                .commonCode(commentDTO.getPostCode())
                .build();
        CommunityComment commonComment = CommunityComment.builder()
                .community(community)
                .commonCommentCode(commentDTO.getCommentCode())
                .commonCommentDesc(commentDTO.getCommentDesc())
                .commonCommentCodeSuper(commentDTO.getParentCommentCode())
                .member(member)
                .build();
        commonCommentService.create(commonComment);

            return ResponseEntity.status(HttpStatus.OK).body(commonComment);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        }

    //일반 게시글 댓글 수정 UPDATE - http://localhost:8080/api/community/comment
    @PutMapping("/community/comment")
    public ResponseEntity<CommunityComment> updateCommonComment(@RequestBody CommentDTO commentDTO){

        CommunityComment target = commonCommentService.showCommonComment(commentDTO.getCommentCode());
        log.info(target.toString());
        target.setCommonCommentDesc(commentDTO.getCommentDesc());
        target.setCommonCommentDate(new Date());


        commonCommentService.update(target);

        return ResponseEntity.status(HttpStatus.OK).body(target);
    }

    //일반 게시글 댓글 삭제 DELETE - http://localhost:8080/api/community/comment/1
    @DeleteMapping("/community/comment/{commonCommentCode}")
    public ResponseEntity<CommunityComment>deleteCommonComment(@PathVariable int commonCommentCode){
        return ResponseEntity.status(HttpStatus.OK).body(commonCommentService.delete(commonCommentCode));
    }


}
