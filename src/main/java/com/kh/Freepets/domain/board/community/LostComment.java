package com.kh.Freepets.domain.board.community;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostComment {
    @Id
    @Column(name="L_COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "lostSequence")
    @SequenceGenerator(name="lostSequence", sequenceName = "SEQ_LOST", allocationSize=1)
    private int lCommentCode;

    @Column(name="L_COMMENT_DESC")
    private String lCommentDesc;
    @Column(name="L_COMMENT_DATE")
    private Date lCommentDate;
    @Column(name="L_COMMENT_CODE_SUPER")
    private int lCommentCodeSuper;
    @Column(name="L_COMMENT_IMG")
    private String lCcommentImg;
    @Column(name="L_COMMENT_REPORT_YN")
    private char lCommentReportYN;

    @ManyToOne
    @JoinColumn(name="LOST_CODE")
    private Lost lost;
    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;

}
