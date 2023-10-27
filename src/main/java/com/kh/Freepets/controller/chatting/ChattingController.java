package com.kh.Freepets.controller.chatting;

import com.kh.Freepets.domain.chatting.Chatting;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.chatting.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
@Slf4j
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class ChattingController {

    @Autowired
    private ChattingService service;
    @Autowired
    private TokenProvider provider;


    @GetMapping("chatting")
    public ResponseEntity<List<Chatting>> allChattingList(@RequestParam String sender, @RequestParam String receiver) {
        String senderId = provider.validateAndGetUserId(sender);
        log.info("senderId : " + senderId);
        log.info("receiverId : " + receiver);
        List<Chatting> chattingList = service.chattingList(senderId, receiver);
        log.info("chattingList : " + chattingList);
        return ResponseEntity.status(HttpStatus.OK).body(chattingList);
    }

    @GetMapping("chatting/{code}")
    public ResponseEntity<Chatting> showMessage(@PathVariable int code) {
        return ResponseEntity.status(HttpStatus.OK).body(service.showMessage(code));
    }

    @PostMapping("chatting")
    public ResponseEntity<Chatting> createMessage(Chatting chatting, String token) {
        String id = provider.validateAndGetUserId(token);
        Member sender = Member.builder()
                .id(id)
                .build();
        chatting.setSender(sender);
        return ResponseEntity.status(HttpStatus.OK).body(service.creatMessage(chatting));
    }


}
