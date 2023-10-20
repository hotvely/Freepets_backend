package com.kh.Freepets.controller.board.sitter;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.domain.util.Paging;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.sitter.SitterService;
import com.kh.Freepets.service.file.FileInputHandler;
import com.kh.Freepets.service.member.MemberService;
import lombok.extern.java.Log;
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

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/*")
@Slf4j
public class SitterController {

    @Autowired
    private SitterService service;
    @Autowired
    private TokenProvider provider;
    @Autowired
    private Paging paging;
    @Autowired
    private MemberService memberService;

    @GetMapping("/sitter")
    public ResponseEntity<List<Sitter>> showAll(@RequestParam(name = "page", defaultValue = "1") int page) {

        Sort sort = Sort.by("sitterCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        Page<Sitter> result = service.showall(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    @GetMapping("/sitter/price/{order}")
    public ResponseEntity<List<Sitter>> showAllPrice(@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable String order) {
        Sort sort = null;
        if(order.equals("asc")) {
            sort = Sort.by("sitterPrice").ascending();
        } else {
            sort = Sort.by("sitterPrice").descending();
        }
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Sitter> result = service.showall(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    @GetMapping("/sitter/{id}")
    public ResponseEntity<BoardDTO> show(@PathVariable int id) {
        Sitter sitter = service.show(id);
        MemberDTO memberDTO = MemberDTO.builder()
                .id(sitter.getMember().getId())
                .nickname(sitter.getMember().getNickname())
                .memberImg(sitter.getMember().getMemberImg())
                .build();
        BoardDTO boardDTO = BoardDTO.builder()
                .boardCode(sitter.getSitterCode())
                .title(sitter.getSitterTitle())
                .desc(sitter.getSitterDesc())
                .sitterLoc(sitter.getSitterLoc())
                .sitterRatings(sitter.getSitterRatings())
                .sitterPrice(sitter.getSitterPrice())
                .memberDTO(memberDTO)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    @GetMapping("/sitter/search")
    public ResponseEntity<List<Sitter>> search(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam String keyword) {
        Sort sort = Sort.by("sitterCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Sitter> result = service.sitterSearch(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    @PostMapping("/sitter")
    public ResponseEntity<Sitter> create(BoardDTO boardDTO) {
        log.info("token : " + boardDTO.getToken());
        String id = provider.validateAndGetUserId(boardDTO.getToken());
        log.info("id : " + id);
        Member member = Member.builder()
                .id(id)
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
        if(service.ratingsCount(id) == null || Integer.parseInt(service.ratingsCount(id)) == 0) {
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
