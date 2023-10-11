package com.kh.Freepets.controller.board.information;

import com.kh.Freepets.domain.board.information.HRComment;
import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.board.information.HRCommentService;
import com.kh.Freepets.service.file.FileInputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/info/*")
public class CommentController {

    @Autowired
    private FileInputHandler handler;
    @Autowired
    private HRCommentService hrService;

    // hrComment

    // 게시글 한 개 댓글 전체 보기
    @GetMapping("/hr/{hospitalReviewCode}/comment")
    public ResponseEntity<List<HRComment>> hrShowAll(@PathVariable int hospitalReviewCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.showBoardAll(hospitalReviewCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 댓글 한 개 보기
    @GetMapping("/hr/comment/{hrCommentCode}")
    public ResponseEntity<HRComment> hrShow(@PathVariable int hrCommentCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.show(hrCommentCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 댓글 작성
    @PostMapping("/hr/comment")
    public ResponseEntity<HRComment> hrCreate(String desc, MultipartFile img, int hospitalReviewCode, String id) {
        try {
            String imgName = handler.fileInput(img);
            HRComment vo = new HRComment();
            vo.setHrCommentDesc(desc);
            vo.setHrCommentImg(imgName);
            vo.setHrCommentReportYn('N');
            HospitalReview hospitalReview = new HospitalReview();
            hospitalReview.setHospitalReviewCode(hospitalReviewCode);
            vo.setHospitalReview(hospitalReview);
            Member member = new Member();
            member.setId(id);
            vo.setMember(member);
            return ResponseEntity.status(HttpStatus.OK).body(hrService.create(vo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 댓글 수정
    @PutMapping("/hr/comment")
    public ResponseEntity<HRComment> hrUpdate(@RequestBody HRComment hrComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.update(hrComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 댓글 삭제
    @DeleteMapping("/hr/comment/{hrCommentCode}")
    public ResponseEntity<HRComment> hrDelete(@PathVariable int hrCommentCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.delete(hrCommentCode));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
