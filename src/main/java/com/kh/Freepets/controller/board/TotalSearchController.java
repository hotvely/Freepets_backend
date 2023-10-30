package com.kh.Freepets.controller.board;

import com.kh.Freepets.domain.util.Paging;
import com.kh.Freepets.service.board.TotalSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(value = {"*"}, maxAge = 6000)
@RequestMapping("/api/*")
public class TotalSearchController {

    @Autowired
    private TotalSearchService service;

    @GetMapping("/totalSearch")
    public ResponseEntity<Paging> getTotalSearch(@RequestParam String search) {
        Paging paging = service.getSearchPage(search);
        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }
}
