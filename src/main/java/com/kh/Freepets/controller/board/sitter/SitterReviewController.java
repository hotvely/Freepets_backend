package com.kh.Freepets.controller.board.sitter;

import com.kh.Freepets.domain.board.sitter.SitterReview;
import com.kh.Freepets.service.board.sitter.SitterReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class SitterReviewController {

    @Autowired
    private SitterReviewService service;

    @GetMapping("/sitterReview")
    public ResponseEntity<List<SitterReview>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showall());
    }

    @GetMapping("/sitterReview/{id}")
    public ResponseEntity<SitterReview> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/sitterReview")
    public ResponseEntity<SitterReview> create(@RequestBody SitterReview sitterReview) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(sitterReview));
    }

    @PutMapping("/sitterReview")
    public ResponseEntity<SitterReview> update(@RequestBody SitterReview sitterReview) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(sitterReview));
    }

    @DeleteMapping("/sitterReview/{id}")
    public ResponseEntity<SitterReview> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
