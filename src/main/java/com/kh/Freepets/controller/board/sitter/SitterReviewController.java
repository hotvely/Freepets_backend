package com.kh.Freepets.controller.board.sitter;

import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.board.sitter.SitterReview;
import com.kh.Freepets.service.board.sitter.SitterReviewService;
import com.kh.Freepets.service.board.sitter.SitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/*")
public class SitterReviewController {

    @Autowired
    private SitterReviewService service;
    @Autowired
    private SitterService sitterService;

    @GetMapping("/sitter/{id}/review") // 시터 한 명당 후기 전체 보기
    public ResponseEntity<List<SitterReview>> showAll(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.showall(id));
    }


    @GetMapping("/sitterReview/{id}") // 후기 한 개 보기
    public ResponseEntity<SitterReview> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/sitterReview") // 후기 작성
    public ResponseEntity<SitterReview> create(SitterReview sitterReview) {
        service.create(sitterReview);
        SitterReview result = service.show(sitterReview.getSitterReviewCode());
        sitterService.updateRatings(result.getSitter().getMember().getId());
        return ResponseEntity.status(HttpStatus.OK).body(service.show(sitterReview.getSitterReviewCode()));
    }

    @PutMapping("/sitterReview") // 후기 수정
    public ResponseEntity<SitterReview> update(@RequestBody SitterReview sitterReview) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(sitterReview));
    }

    @DeleteMapping("/sitterReview/{id}") // 후기 삭제
    public ResponseEntity<SitterReview> delete(@PathVariable int id) {
        SitterReview result = service.show(id);
        service.delete(id);
        String memberId = result.getSitter().getMember().getId();
        sitterService.updateRatings(memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
