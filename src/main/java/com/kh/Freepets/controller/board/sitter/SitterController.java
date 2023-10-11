package com.kh.Freepets.controller.board.sitter;

import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.board.sitter.SitterService;
import com.kh.Freepets.service.file.FileInputHandler;
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
@RequestMapping("/api/*")
public class SitterController {

    @Autowired
    private FileInputHandler handler;
    @Autowired
    private SitterService service;

    @GetMapping("/sitter")
    public ResponseEntity<List<Sitter>> showAll(@RequestParam(name = "page", defaultValue = "1") int page) {

        Sort sort = Sort.by("sitterCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        Page<Sitter> result = service.showall(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    @GetMapping("/sitter/{id}")
    public ResponseEntity<Sitter> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/sitter")
    public ResponseEntity<Sitter> create(String title, String loc, int price, String desc, MultipartFile Img, String id) {
        String imgName = handler.fileInput(Img);
        Sitter sitter = new Sitter();
        sitter.setSitterTitle(title);
        sitter.setSitterLoc(loc);
        sitter.setSitterPrice(price);
        sitter.setSitterDesc(desc);
        sitter.setSitterImg(imgName);
        Member member = new Member();
        member.setId(id);
        sitter.setMember(member);
        service.create(sitter);
        List<Sitter> target = service.isSitter(sitter.getMember().getId());
        if(!target.isEmpty()) {
            service.updateRatings(sitter.getMember().getId());
        }
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
