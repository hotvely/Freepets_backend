package com.kh.Freepets.controller.board.freeMarket;

import com.kh.Freepets.domain.board.freeMarket.FreeMarketReview;
import com.kh.Freepets.service.board.freeMarket.FreeMarketReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class FreeMarketReviewController {

    @Autowired
    private FreeMarketReviewService service;

    @GetMapping("/freemarketReview")
    public ResponseEntity<List<FreeMarketReview>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showall());
    }

    @GetMapping("/freemarketReview/{id}")
    public ResponseEntity<FreeMarketReview> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/freemarketReview")
    public ResponseEntity<FreeMarketReview> create(@RequestBody FreeMarketReview freeMarketReview) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(freeMarketReview));
    }

    @PutMapping("/freemarketReview")
    public ResponseEntity<FreeMarketReview> update(@RequestBody FreeMarketReview freeMarketReview) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(freeMarketReview));
    }

    @DeleteMapping("/freemarketReview/{id}")
    public ResponseEntity<FreeMarketReview> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
