package com.kh.Freepets.controller.board.information;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.domain.util.Paging;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.file.FileInputHandler;
import com.kh.Freepets.domain.board.information.*;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.board.information.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/api/info/*")
public class BoardController
{
    @Autowired
    private HospitalReviewService hrService;
    @Autowired
    private HrLikeService hrLikeService;
    @Autowired
    private TokenProvider provider;

    // hospitalReview

    // 게시글 전체 보기
    @GetMapping("/hr")
    public ResponseEntity<Paging> hrShowAll(@RequestParam(name = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by("hospitalReviewCode").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);

        Page<HospitalReview> result = hrService.showAll(pageable);
        Paging paging = new Paging();
        paging.setHospitalReviewList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        paging.setGetNumber(result.getNumber());
        paging.setHasNext(result.hasNext());
        paging.setHasPrev(result.hasPrevious());
        paging.setFirst(result.isFirst());
        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    @GetMapping("/hr/search")
    public ResponseEntity<Paging> hrSearch(@RequestParam(name = "page", defaultValue = "1")int page, @RequestParam String keyword, @RequestParam int select) {
        log.info("page : " + page);
        log.info("select : " + select);
        log.info("keyword : " + keyword);
        Sort sort = Sort.by("hospitalReviewCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<HospitalReview> result = hrService.searchHr(keyword, select, pageable);
        Paging paging = new Paging();
        paging.setTotalPages(result.getTotalPages());
        paging.setHospitalReviewList(result.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    // 게시글 한 개 보기
    @GetMapping("/hr/{hospitalReviewCode}")
    public ResponseEntity<BoardDTO> hrShow(@PathVariable int hospitalReviewCode) {
        HospitalReview hospitalReview = hrService.show(hospitalReviewCode);

        MemberDTO memberDTO = MemberDTO.builder()
                .id(hospitalReview.getMember().getId())
                .nickname(hospitalReview.getMember().getNickname())
                .memberImg(hospitalReview.getMember().getMemberImg())
                .build();

        BoardDTO boardDTO = BoardDTO.builder()
                .boardCode(hospitalReviewCode)
                .hospitalName(hospitalReview.getHospitalName())
                .hospitalAddress(hospitalReview.getHospitalAddress())
                .title(hospitalReview.getHospitalReviewTitle())
                .desc(hospitalReview.getHospitalReviewDesc())
                .commonDate(hospitalReview.getHospitalReviewDate())
                .viewCount(hospitalReview.getHospitalReviewViews())
                .commentCount(hospitalReview.getHospitalReviewCommentCount())
                .likeCount(hospitalReview.getHospitalReviewLike())
                .memberDTO(memberDTO)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    // 게시글 작성
    @PostMapping("/hr")
    public ResponseEntity<HospitalReview> hrCreate(BoardDTO boardDTO) {
        String id = provider.validateAndGetUserId(boardDTO.getToken());
        log.info("hospitalName : " + boardDTO.getHospitalName());
        log.info("hospitalAddress : " + boardDTO.getHospitalAddress());
        Member member = Member.builder()
                    .id(id)
                    .build();
        HospitalReview hospitalReview = HospitalReview.builder()
                    .hospitalReviewCode(boardDTO.getBoardCode())
                    .hospitalName(boardDTO.getHospitalName())
                    .hospitalAddress(boardDTO.getHospitalAddress())
                    .hospitalReviewTitle(boardDTO.getTitle())
                    .hospitalReviewDesc(boardDTO.getDesc())
                    .hospitalReviewDeleteYn('N')
                    .member(member)
                    .build();

        hrService.create(hospitalReview);
        return ResponseEntity.status(HttpStatus.OK).body(hrService.show(hospitalReview.getHospitalReviewCode()));
    }

    // 게시글 수정
    @PutMapping("/hr")
    public ResponseEntity<HospitalReview> hrUpdate(@RequestBody HospitalReview hospitalReview) {
        return ResponseEntity.status(HttpStatus.OK).body(hrService.update(hospitalReview));
    }

    // 게시글 삭제
    @DeleteMapping("/hr/{hospitalReviewCode}")
    public ResponseEntity<HospitalReview> hrDelete(@PathVariable int hospitalReviewCode) {
        return ResponseEntity.status(HttpStatus.OK).body(hrService.delete(hospitalReviewCode));
    }


    // 게시글 좋아요, 좋아요 취소 & 개수 처리
    @PostMapping("/hr/like")
    public ResponseEntity<BoardDTO> hrUpdateLike(HrLike hrLike) {
        HrLike target = hrLikeService.likeMember(hrLike.getMember().getId(), hrLike.getHospitalReview().getHospitalReviewCode());
        if (target == null) { // 유저가 해당 글 좋아요 한 적이 없으면 좋아요, 좋아요 + 1
            hrService.updateLike(hrLike.getHospitalReview().getHospitalReviewCode());
            log.info("좋아요 누름");
            hrLikeService.hrAddLike(hrLike);
        } else { // 한 적 있으면 좋아요 취소, 좋아요 - 1
            hrService.deleteLike(hrLike.getHospitalReview().getHospitalReviewCode());
            log.info("좋아요 취소 누름");
            hrLikeService.hrDeleteLike(target.getHrLikeCode());
        }
        HospitalReview hospitalReview = hrService.show(hrLike.getHospitalReview().getHospitalReviewCode());
        MemberDTO memberDTO = MemberDTO.builder()
                .id(hospitalReview.getMember().getId())
                .nickname(hospitalReview.getMember().getNickname())
                .memberImg(hospitalReview.getMember().getMemberImg())
                .build();
        BoardDTO boardDTO = BoardDTO.builder()
                .boardCode(hospitalReview.getHospitalReviewCode())
                .hospitalName(hospitalReview.getHospitalName())
                .hospitalAddress(hospitalReview.getHospitalAddress())
                .title(hospitalReview.getHospitalReviewTitle())
                .desc(hospitalReview.getHospitalReviewDesc())
                .commonDate(hospitalReview.getHospitalReviewDate())
                .viewCount(hospitalReview.getHospitalReviewViews())
                .commentCount(hospitalReview.getHospitalReviewCommentCount())
                .likeCount(hospitalReview.getHospitalReviewLike())
                .memberDTO(memberDTO)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    // 게시글 좋아요 별로 보기
    @GetMapping("/hr/orderlike")
    public ResponseEntity<Paging> hrShowLike(@RequestParam(name = "page", defaultValue = "1") int page)
    {
        Sort sort = Sort.by("hospitalReviewLike").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<HospitalReview> result = hrService.showAll(pageable);
        Paging paging = new Paging();
        paging.setTotalPages(result.getTotalPages());
        paging.setHospitalReviewList(result.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    // 게시글 댓글 수 별로 보기
    @GetMapping("/hr/ordercomment")
    public ResponseEntity<Paging> hrShowComment(@RequestParam(name = "page", defaultValue = "1") int page)
    {
        Sort sort = Sort.by("hospitalReviewCommentCount").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<HospitalReview> result = hrService.showAll(pageable);
        Paging paging = new Paging();
        paging.setTotalPages(result.getTotalPages());
        paging.setHospitalReviewList(result.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }
}
