package com.kh.Freepets.controller.board.sitter;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.board.sitter.SitterService;
import com.kh.Freepets.service.file.FileInputHandler;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/*")
@Slf4j
public class SitterController {

    @Autowired
    private FileInputHandler handler;
    @Value("${freepets.upload.path}")
    private String uploadPath;
    @Autowired
    private SitterService service;

    @PostMapping("/img")
    public ResponseEntity<String> imgReturn(@RequestParam(name = "file", required = true) MultipartFile file) {
        String originalFile = file.getOriginalFilename();
        String realFile = originalFile.substring(originalFile.lastIndexOf("\\")+1);
        String uuid = UUID.randomUUID().toString();
        String saveFile = uploadPath + File.separator + uuid + "_" + realFile;
        Path pathFile = Paths.get(saveFile);
        try {
            file.transferTo(pathFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(uuid + "_" + realFile);
    }

    @GetMapping("/sitter")
    public ResponseEntity<List<Sitter>> showAll(@RequestParam(name = "page", defaultValue = "1") int page) {

        Sort sort = Sort.by("sitterCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        Page<Sitter> result = service.showall(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    @GetMapping("/sitter/price")
    public ResponseEntity<List<Sitter>> showAllPrice(@RequestParam(name = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by("sitterPrice").ascending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Sitter> result = service.showall(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    @GetMapping("/sitter/{id}")
    public ResponseEntity<Sitter> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/sitter")
    public ResponseEntity<Sitter> create(BoardDTO boardDTO) {
        log.info("file 잘 들어 왔니 : " + boardDTO.getUploadfileUrl());
        Member member = Member.builder()
                .id(boardDTO.getMemberDTO().getId())
                .build();
        Sitter sitter = Sitter.builder()
                .sitterCode(boardDTO.getBoardCode())
                .sitterTitle(boardDTO.getTitle())
                .sitterLoc(boardDTO.getSitterLoc())
                .sitterPrice(boardDTO.getSitterPrice())
                .sitterRatings(boardDTO.getSitterRatings())
                .sitterDesc(boardDTO.getDesc())
                .member(member)
                .build();
        service.create(sitter);
        if(service.ratingsCount(boardDTO.getMemberDTO().getId()) == null || Integer.parseInt(service.ratingsCount(boardDTO.getMemberDTO().getId())) == 0) {
            sitter.setSitterRatings(0);
        }
        else service.updateRatings(sitter.getMember().getId());
        return ResponseEntity.status(HttpStatus.OK).body(service.show(sitter.getSitterCode()));
    }

    @PutMapping("/sitter")
    public ResponseEntity<Sitter> update(@RequestBody Sitter sitter) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(sitter));
    }

    @DeleteMapping("/sitter/{id}") // 시터 글 삭제 X default y or n column 추가 글은 남는데 안 보이게
    public ResponseEntity<Sitter> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
