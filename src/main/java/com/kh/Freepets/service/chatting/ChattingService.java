package com.kh.Freepets.service.chatting;

import com.kh.Freepets.domain.chatting.Chatting;
import com.kh.Freepets.repo.chatting.ChattingDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChattingService {

    @Autowired
    private ChattingDAO dao;

    public List<Chatting> chattingList (String sender, String receiver) {
        return dao.senderList(sender, receiver);
    }

    public Chatting creatMessage(Chatting chatting) {
      return dao.save(chatting);
    }

    public Chatting showMessage(int code) {
        return dao.findById(code).orElse(null);
    }

    public Chatting deleteMessgae(int id) {
        Chatting chatting = dao.findById(id).orElse(null);
        dao.delete(chatting);
        return chatting;
    }
}
