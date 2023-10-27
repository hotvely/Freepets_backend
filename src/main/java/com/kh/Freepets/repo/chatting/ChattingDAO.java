package com.kh.Freepets.repo.chatting;

import com.kh.Freepets.domain.chatting.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChattingDAO extends JpaRepository<Chatting, Integer> {

    @Query(value = "SELECT * FROM CHATTING WHERE (SENDER = :sender AND RECEIVER = :receiver) OR (RECEIVER = :sender AND SENDER = :receiver) ORDER BY CHATTING_DATE", nativeQuery = true)
    List<Chatting> senderList(String sender, String receiver);
}
