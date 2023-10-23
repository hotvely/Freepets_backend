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
    private MemberService memberService;

    @GetMapping("/sitter")
    public ResponseEntity<Paging> showAll(@RequestParam(name = "page", defaultValue = "1") int page) {

        Sort sort = Sort.by("sitterCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Sitter> result = service.showall(pageable);
        Paging paging = new Paging();
        paging.setSitterList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        paging.setGetNumber(result.getNumber());
        paging.setHasNext(result.hasNext());
        paging.setHasPrev(result.hasPrevious());
        paging.setFirst(result.isFirst());

        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    @GetMapping("/sitter/price/{order}")
    public ResponseEntity<Paging> showAllPrice(@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable String order) {
        Sort sort = null;
        if(order.equals("asc")) {
            sort = Sort.by("sitterPrice").ascending();
        } else {
            sort = Sort.by("sitterPrice").descending();
        }
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Sitter> result = service.showall(pageable);
        Paging paging = new Paging();
        paging.setSitterList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(paging);
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
    public ResponseEntity<Paging> search(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam String keyword) {
        Sort sort = Sort.by("sitterCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Sitter> result = service.sitterSearch(keyword, pageable);
        Paging paging = new Paging();
        paging.setSitterList(result.getContent());
        paging.setTotalCount(result.getTotalElements());
        paging.setTotalPages(result.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(paging);
    }

    @PostMapping("/sitter")
    public ResponseEntity<BoardDTO> create(BoardDTO boardDTO) {
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
        service.updateRatings(sitter.getMember().getId());
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    @PutMapping("/sitter")
    public ResponseEntity<BoardDTO> update(@RequestBody BoardDTO boardDTO) {
        String id = provider.validateAndGetUserId(boardDTO.getToken());
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
        service.update(sitter);
        service.updateRatings(sitter.getMember().getId());
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    @DeleteMapping("/sitter/{id}")
    public ResponseEntity<Sitter> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
