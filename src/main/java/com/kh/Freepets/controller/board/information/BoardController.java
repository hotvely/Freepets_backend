package com.kh.Freepets.controller.board.information;

import com.kh.Freepets.service.file.FileInputHandler;
import com.kh.Freepets.domain.board.information.*;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.board.information.*;
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
@RequestMapping("/api/info/*")
public class BoardController
{

    @Autowired
    private FileInputHandler handler;
    @Autowired
    private HospitalReviewService hrService;
    @Autowired
    private HrLikeService hrLikeService;

    // hospitalReview

    // 게시글 전체 보기
    @GetMapping("/hr")
    public ResponseEntity<List<HospitalReview>> hrShowAll(@RequestParam(name = "page", defaultValue = "1") int page)
    {
        try
        {
            Sort sort = Sort.by("hospitalReviewCode").descending();
            Pageable pageable = PageRequest.of(page - 1, 10, sort);

            Page<HospitalReview> result = hrService.showAll(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 한 개 보기
    @GetMapping("/hr/{hospitalReviewCode}")
    public ResponseEntity<HospitalReview> hrShow(@PathVariable int hospitalReviewCode)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.show(hospitalReviewCode));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 작성
    @PostMapping("/hr")
    public ResponseEntity<HospitalReview> hrCreate(String hospitalName, String hospitalAddress, String title, String desc, String id, MultipartFile file)
    {
        try
        {
            String fileName = handler.fileInput(file);
            HospitalReview vo = new HospitalReview();
            vo.setHospitalName(hospitalName);
            vo.setHospitalAddress(hospitalAddress);
            vo.setHospitalReviewTitle(title);
            vo.setHospitalReviewDesc(desc);
            vo.setHospitalReviewFileUrl(fileName);
            vo.setHospitalReviewDeleteYn('N');
            Member member = new Member();
            member.setId(id);
            vo.setMember(member);
            return ResponseEntity.status(HttpStatus.OK).body(hrService.create(vo));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 수정
    @PutMapping("/hr")
    public ResponseEntity<HospitalReview> hrUpdate(@RequestBody HospitalReview hospitalReview)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.update(hospitalReview));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/hr/{hospitalReviewCode}")
    public ResponseEntity<HospitalReview> hrDelete(@PathVariable int hospitalReviewCode)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(hrService.delete(hospitalReviewCode));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    // 게시글 좋아요 & 좋아요 개수 처리
    @PostMapping("/hr/like")
    public ResponseEntity<HrLike> hrUpdateLike(@RequestBody HrLike hrLike)
    {
        try
        {
            HrLike target = hrLikeService.likeMember(hrLike.getMember().getId(), hrLike.getHospitalReview().getHospitalReviewCode());
            if (target == null)
            {
                hrService.updateLike(hrLike.getHospitalReview().getHospitalReviewCode());
                return ResponseEntity.status(HttpStatus.OK).body(hrLikeService.hrAddLike(hrLike));
            }
            else return null;
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 취소 & 좋아요 개수 처리
    @DeleteMapping("/hr/like/{hrLikeCode}")
    public ResponseEntity<HrLike> hrDeleteLike(@PathVariable int hrLikeCode)
    {
        try
        {
            HrLike hrLike = hrLikeService.show(hrLikeCode);
            HrLike target = hrLikeService.likeMember(hrLike.getMember().getId(), hrLike.getHospitalReview().getHospitalReviewCode());
            if (target != null)
            {
                hrService.deleteLike(hrLike.getHospitalReview().getHospitalReviewCode());
                return ResponseEntity.status(HttpStatus.OK).body(hrLikeService.hrDeleteLike(hrLikeCode));
            }
            else return null;
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 게시글 좋아요 별로 보기
    @GetMapping("/hr/orderlike")
    public ResponseEntity<List<HospitalReview>> hrShowLike(@RequestParam(name = "page", defaultValue = "1") int page)
    {
        Sort sort = Sort.by("hospitalReviewLike").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<HospitalReview> result = hrService.showAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    // 게시글 댓글 수 별로 보기
    @GetMapping("/hr/ordercomment")
    public ResponseEntity<List<HospitalReview>> hrShowComment(@RequestParam(name = "page", defaultValue = "1") int page)
    {
        Sort sort = Sort.by("hospitalReviewCommentCount").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<HospitalReview> result = hrService.showAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }
}
