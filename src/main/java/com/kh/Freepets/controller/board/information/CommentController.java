package com.kh.Freepets.controller.board.information;

import com.kh.Freepets.domain.board.information.HRComment;
import com.kh.Freepets.domain.board.information.PRComment;
import com.kh.Freepets.domain.board.information.VIComment;
import com.kh.Freepets.service.board.information.HRCommentService;
import com.kh.Freepets.service.board.information.PRCommentService;
import com.kh.Freepets.service.board.information.VICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/info/*")
public class CommentController {

    @Autowired
    private HRCommentService hrService;
    @Autowired
    private PRCommentService prService;
    @Autowired
    private VICommentService viService;

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
    public ResponseEntity<HRComment> hrCreate(@RequestBody HRComment hrComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.create(hrComment));
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

    // prComment

    @GetMapping("/pr/{productReviewCode}/comment")
    public ResponseEntity<List<PRComment>> prShowAll(@PathVariable int productReviewCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.showBoardAll(productReviewCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/pr/comment")
    public ResponseEntity<PRComment> prCreate(@RequestBody PRComment prComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.create(prComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/pr/comment")
    public ResponseEntity<PRComment> prUpdate(@RequestBody PRComment prComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.update(prComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/pr/comment/{prCommentCode}")
    public ResponseEntity<PRComment> prDelete(@PathVariable int prCommentCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.delete(prCommentCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // viComment

    @GetMapping("/vi/{viCommentCode}/comment")
    public ResponseEntity<List<VIComment>> viShowAll(@PathVariable int viCommentCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.showBoardAll(viCommentCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/vi/comment")
    public ResponseEntity<VIComment> prCreate(@RequestBody VIComment viComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.create(viComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/vi/comment")
    public ResponseEntity<VIComment> prUpdate(@RequestBody VIComment viComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.update(viComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/vi/comment/{viCommentCode}")
    public ResponseEntity<VIComment> viDelete(@PathVariable int viCommentCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.delete(viCommentCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
