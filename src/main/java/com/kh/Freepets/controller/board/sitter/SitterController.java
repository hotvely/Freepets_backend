package com.kh.Freepets.controller.board.sitter;

import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.service.board.sitter.SitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class SitterController {

    @Autowired
    private SitterService service;

    @GetMapping("/sitter")
    public ResponseEntity<List<Sitter>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showall());
    }

    @GetMapping("/sitter/{id}")
    public ResponseEntity<Sitter> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/sitter")
    public ResponseEntity<Sitter> create(@RequestBody Sitter sitter) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(sitter));
    }

    @PutMapping("/sitter")
    public ResponseEntity<Sitter> update(@RequestBody Sitter sitter) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(sitter));
    }

    @DeleteMapping("/sitter/{id}")
    public ResponseEntity<Sitter> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
