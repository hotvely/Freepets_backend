package com.kh.Freepets.domain.board.notice;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeComment
{
    @Id
    @Column(name = "NOTICE_COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "noticeSequence")
    @SequenceGenerator(name = "noticeSequence", sequenceName = "SEQ_NOTICE", allocationSize = 1)
    private int noticeCommentCode;

    @Column(name = "NOTICE_COMMENT_DESC")
    private String noticeCommentDesc;
    @Column(name = "NOTICE_COMMENT_DATE")
    private Date noticeCommentDate;
    @Column(name = "NOTICE_COMMENT_CODE_SUPER")
    private int noticeCommentCodeSuper;


    @ManyToOne
    @JoinColumn(name = "NOTICE_CODE")
    private Notice notice;
    
    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;

}
