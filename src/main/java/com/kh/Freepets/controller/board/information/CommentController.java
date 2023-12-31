package com.kh.Freepets.controller.board.information;

import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.information.HRComment;
import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.information.HRCommentService;
import com.kh.Freepets.service.board.information.HospitalReviewService;
import com.kh.Freepets.service.file.FileInputHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/info/*")
@Slf4j
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class CommentController {

    @Autowired
    private HRCommentService hrService;
    @Autowired
    private HospitalReviewService hospitalReviewService;
    @Autowired
    private TokenProvider provider;

    // hrComment

    // 게시글 한 개 댓글 전체 보기
    @GetMapping("/hr/{hospitalReviewCode}/comment")
    public ResponseEntity<List<HRComment>> hrShowAll(@PathVariable int hospitalReviewCode) {
        return ResponseEntity.status(HttpStatus.OK).body(hrService.showBoardAll(hospitalReviewCode));
    }

    @GetMapping("/hr/comment/one/{code}")
    public ResponseEntity<HRComment> hrShow(@PathVariable int code) {
        return ResponseEntity.status(HttpStatus.OK).body(hrService.show(code));
    }

    // 부모 댓글에 달린 대댓글들 불러 오기
    @GetMapping("/hr/comment/{superCode}")
    public ResponseEntity<List<HRComment>> hrShowReAll(@PathVariable int superCode) {
        return ResponseEntity.status(HttpStatus.OK).body(hrService.showReCommentAll(superCode));
    }

    // 댓글 작성
    @PostMapping("/hr/comment")
    public ResponseEntity<HRComment> hrCreate(@RequestBody CommentDTO commentDTO) {
        String id = provider.validateAndGetUserId(commentDTO.getToken());
        Member member = Member.builder()
                .id(id)
                .build();
        HospitalReview hospitalReview = HospitalReview.builder()
                .hospitalReviewCode(commentDTO.getPostCode())
                .build();
        HRComment hrComment = HRComment.builder()
                .hospitalReview(hospitalReview)
                .hrCommentCode(commentDTO.getCommentCode())
                .hrCommentDesc(commentDTO.getCommentDesc())
                .superHrCommentCode(commentDTO.getParentCommentCode())
                .hrCommentReportYn('N')
                .member(member)
                .build();
        HRComment result = hrService.create(hrComment);
        return ResponseEntity.status(HttpStatus.OK).body(hrService.show(result.getHrCommentCode()));
    }

    // 댓글 수정
    @PutMapping("/hr/comment")
    public ResponseEntity<HRComment> hrUpdate(@RequestBody CommentDTO commentDTO) {
        HRComment hrComment = hrService.show(commentDTO.getCommentCode());
        Member member = Member.builder()
                .id(hrComment.getMember().getId())
                .build();
        HospitalReview hospitalReview = HospitalReview.builder()
                .hospitalReviewCode(hrComment.getHospitalReview().getHospitalReviewCode())
                .build();
        HRComment result = HRComment.builder()
                .hospitalReview(hospitalReview)
                .hrCommentCode(commentDTO.getCommentCode())
                .hrCommentDesc(commentDTO.getCommentDesc())
                .hrCommentDate(hrComment.getHrCommentDate())
                .superHrCommentCode(hrComment.getSuperHrCommentCode())
                .hrCommentReportYn('N')
                .member(member)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(hrService.update(result));
    }

    // 댓글 삭제
    @DeleteMapping("/hr/comment/{hrCommentCode}")
    public ResponseEntity<HRComment> hrDelete(@PathVariable int hrCommentCode) {
        return ResponseEntity.status(HttpStatus.OK).body(hrService.delete(hrCommentCode));
    }

}
