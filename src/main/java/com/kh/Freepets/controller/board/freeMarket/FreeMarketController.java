package com.kh.Freepets.controller.board.freeMarket;

import com.kh.Freepets.domain.board.freeMarket.FreeMarket;
import com.kh.Freepets.service.board.freeMarket.FreeMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class FreeMarketController {

    @Autowired
    private FreeMarketService service;

    @GetMapping("/freemarket")
    public ResponseEntity<List<FreeMarket>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showall());
    }

    @GetMapping("/freemarket/{id}")
    public ResponseEntity<FreeMarket> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/freemarket")
    public ResponseEntity<FreeMarket> create(@RequestBody FreeMarket freeMarket) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(freeMarket));
    }

    @PutMapping("/freemarket")
    public ResponseEntity<FreeMarket> update(@RequestBody FreeMarket freeMarket) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(freeMarket));
    }

    @DeleteMapping("/freemarket/{id}")
    public ResponseEntity<FreeMarket> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
