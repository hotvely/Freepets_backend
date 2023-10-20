package com.kh.Freepets.domain.board.notice;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
public class Notice
{
    @Id
    @Column(name = "NOTICE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "noticeSequence")
    @SequenceGenerator(name = "noticeSequence", sequenceName = "SEQ_NOTICE", allocationSize = 1)
    private int noticeCode;


    @Column(name = "NOTICE_TITLE")
    private String noticeTitle;
    @Column(name = "NOTICE_ADDFILE_URL")
    private String noticeAddFileUrl;
    @Column(name = "NOTICE_DATE")
    private Date noticeDate;
    @Column(name = "NOTICE_VIEWS")
    private int noticeViews;
    @Column(name = "NOTICE_DESC")
    private String noticeDesc;

    @Column(name = "NOTICE_LIKE")
    private int noticeLike;
    @Column(name = "NOTICE_COMMENT_COUNT")
    private int noticeCommentCount;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}
