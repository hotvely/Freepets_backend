package com.kh.Freepets.controller.board.information;

import com.kh.Freepets.domain.board.information.*;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.board.information.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/info/*")
public class BoardController {

    @Autowired
    private HospitalReviewService hrService;
    @Autowired
    private HrLikeService hrLikeService;
    @Autowired
    private ProductReviewService prService;
    @Autowired
    private PrLikeService prLikeService;
    @Autowired
    private VideoInfoService viService;
    @Autowired
    private ViLikeService viLikeService;


    // hospitalReview
    
    // 게시글 전체 보기
    @GetMapping("/hr")
    public ResponseEntity<List<HospitalReview>> hrShowAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.showAll());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 한 개 보기
    @GetMapping("/hr/{hospitalReviewCode}")
    public ResponseEntity<HospitalReview> hrShow(@PathVariable int hospitalReviewCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.show(hospitalReviewCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 작성
    @PostMapping("/hr")
    public ResponseEntity<HospitalReview> hrCreate(@RequestBody HospitalReview hospitalReview) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.create(hospitalReview));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 수정
    @PutMapping("/hr")
    public ResponseEntity<HospitalReview> hrUpdate(@RequestBody HospitalReview hospitalReview) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.update(hospitalReview));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 삭제
    @DeleteMapping("/hr/{hospitalReviewCode}")
    public ResponseEntity<HospitalReview> hrDelete(@PathVariable int hospitalReviewCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.delete(hospitalReviewCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 & 좋아요 개수 처리
    @PostMapping("/hr/like")
    public ResponseEntity<HrLike> hrUpdateLike(@RequestBody HrLike hrLike) {
        try {
            HrLike target = hrLikeService.likeMember(hrLike.getMember().getId(), hrLike.getHospitalReview().getHospitalReviewCode());
            if (target == null) {
                hrService.updateLike(hrLike.getHospitalReview().getHospitalReviewCode());
                return ResponseEntity.status(HttpStatus.OK).body(hrLikeService.hrAddLike(hrLike));
            } else return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 취소 & 좋아요 개수 처리
    @DeleteMapping("/hr/like/{hrLikeCode}")
    public ResponseEntity<HrLike> hrDeleteLike(@PathVariable int hrLikeCode) {
        try {
            HrLike hrLike = hrLikeService.show(hrLikeCode);
            HrLike target = hrLikeService.likeMember(hrLike.getMember().getId(), hrLike.getHospitalReview().getHospitalReviewCode());
            if(target != null) {
                hrService.deleteLike(hrLike.getHospitalReview().getHospitalReviewCode());
                return ResponseEntity.status(HttpStatus.OK).body(hrLikeService.hrDeleteLike(hrLikeCode));
            } else return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 별로 보기
    @GetMapping("/hr/orderlike")
    public ResponseEntity<List<HospitalReview>> hrShowLike() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.showLike());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 댓글 수 별로 보기
    @GetMapping("/hr/ordercomment")
    public ResponseEntity<List<HospitalReview>> hrShowComment() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.showComment());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // productReview

    // 게시글 전체 보기
    @GetMapping("/pr")
    public ResponseEntity<List<ProductReview>> prShowAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.showAll());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 한 개 보기
    @GetMapping("/pr/{productReviewCode}")
    public ResponseEntity<ProductReview> prShow(@PathVariable int productReviewCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.show(productReviewCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 작성
    @PostMapping("/pr")
    public ResponseEntity<ProductReview> prCreate(@RequestBody ProductReview productReview) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.create(productReview));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 수정
    @PutMapping("/pr")
    public ResponseEntity<ProductReview> prUpdate(@RequestBody ProductReview productReview) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.update(productReview));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 삭제
    @DeleteMapping("/pr/{productReviewCode}")
    public ResponseEntity<ProductReview> prDelete(@PathVariable int productReviewCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.delete(productReviewCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 좋아요 & 좋아요 개수 처리
    @PostMapping("/pr/like")
    public ResponseEntity<PrLike> prUpdateLike(@RequestBody PrLike prLike) {
        try {
            PrLike target = prLikeService.likeMember(prLike.getMember().getId(), prLike.getProductReview().getProductReviewCode());
            if (target == null) {
                prService.updateLike(prLike.getProductReview().getProductReviewCode());
                return ResponseEntity.status(HttpStatus.OK).body(prLikeService.prAddLike(prLike));
            } else return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 좋아요 취소 & 좋아요 개수 처리
    @DeleteMapping("/pr/like/{prLikeCode}")
    public ResponseEntity<PrLike> prDeleteLike(@PathVariable int prLikeCode) {
        try {
            PrLike prLike = prLikeService.show(prLikeCode);
            PrLike target = prLikeService.likeMember(prLike.getMember().getId(), prLike.getProductReview().getProductReviewCode());
            if(target != null) {
                prService.deleteLike(prLike.getProductReview().getProductReviewCode());
                return ResponseEntity.status(HttpStatus.OK).body(prLikeService.prDeleteLike(prLikeCode));
            } else return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 좋아요 정렬
    @GetMapping("/pr/orderlike")
    public ResponseEntity<List<ProductReview>> prShowLike() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.showLike());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 댓글 수 정렬
    @GetMapping("/pr/ordercomment")
    public ResponseEntity<List<ProductReview>> prShowComment() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(prService.showComment());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // videoInfo
    
    // 게시글 전체 보기
    @GetMapping("/vi")
    public ResponseEntity<List<VideoInfo>> viShowAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.showAll());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 한 개 보기
    @GetMapping("/vi/{videoInfoCode}")
    public ResponseEntity<VideoInfo> viShow(@PathVariable int videoInfoCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.show(videoInfoCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 작성
    @PostMapping("/vi")
    public ResponseEntity<VideoInfo> viCreate(@RequestBody VideoInfo videoInfo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.create(videoInfo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 수정
    @PutMapping("/vi")
    public ResponseEntity<VideoInfo> viUpdate(@RequestBody VideoInfo videoInfo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.update(videoInfo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 삭제
    @DeleteMapping("/vi/{videoInfoCode}")
    public ResponseEntity<VideoInfo> viDelete(@PathVariable int videoInfoCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.delete(videoInfoCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 & 좋아요 개수 처리
    @PostMapping("/vi/like")
    public ResponseEntity<ViLike> viUpdateLike(@RequestBody ViLike viLike) {
        try {
            ViLike target = viLikeService.likeMember(viLike.getMember().getId(), viLike.getVideoInfo().getVideoInfoCode());
            if (target == null) {
                viService.updateLike(viLike.getVideoInfo().getVideoInfoCode());
                return ResponseEntity.status(HttpStatus.OK).body(viLikeService.viAddLike(viLike));
            } else return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 취소 & 좋아요 개수 처리
    @DeleteMapping("/vi/like/{viLikeCode}")
    public ResponseEntity<ViLike> viDeleteLike(@PathVariable int viLikeCode) {
        try {
            ViLike viLike = viLikeService.show(viLikeCode);
            ViLike target = viLikeService.likeMember(viLike.getMember().getId(), viLike.getVideoInfo().getVideoInfoCode());
            if(target != null) {
                viService.deleteLike(viLike.getVideoInfo().getVideoInfoCode());
                return ResponseEntity.status(HttpStatus.OK).body(viLikeService.viDeleteLike(viLikeCode));
            } else return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 좋아요 정렬
    @GetMapping("/vi/orderlike")
    public ResponseEntity<List<VideoInfo>> viShowLike() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.showLike());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // 게시글 댓글 수 정렬
    @GetMapping("/vi/ordercomment")
    public ResponseEntity<List<VideoInfo>> viShowComment() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viService.showComment());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }




}
