package com.kh.Freepets.domain.chatting;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Chatting {

    @Id
    @Column(name = "CHATTING_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "chattingSequence")
    @SequenceGenerator(name = "chattingSequence", sequenceName = "SEQ_CHATTING", allocationSize = 1)
    private int chattingCode;

    @Column(name = "CHATTING_DATE")
    private Date chattingDate;

    @ManyToOne
    @JoinColumn(name = "SENDER")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "RECEIVER")
    private Member receiver;

    @Column(name = "CHATTING_CONTENT")
    private String chattingContent;


//    @PrePersist
//    protected void onCreate() {
//        chattingDate = new Date();
//    }

}
