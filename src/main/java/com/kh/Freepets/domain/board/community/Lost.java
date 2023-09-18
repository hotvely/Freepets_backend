package com.kh.Freepets.domain.board.community;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Lost {
    @Id
    @Column(name="LOST_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "lostSequence")
    @SequenceGenerator(name="lostSequence", sequenceName = "SEQ_LOST", allocationSize=1)
    private int lostCode;


    @Column(name="LOST_TITLE")
    private String lostTitle;
    @Column(name="LOST_ADDFILE_URL")
    private String lostAddFileUrl;
    @Column(name="LOST_URL")
    private String lostUrl;
    @Column(name="LOST_DATE")
    private Date lostDate;
    @Column(name="LOST_VIEWS")
    private int lostViews;
    @Column(name="LOST_DESC")
    private String lostDesc;
    @Column(name="LOST_REPORT_YN")
    private char lostReportYN;
    @Column(name="LOST_LIKE")
    private int lostLike;
    @Column(name="LOST_COMMENT_COUNT")
    private int lostCommentCount;

    @ManyToOne
    @JoinColumn(name="id")
    private Member member;
}
