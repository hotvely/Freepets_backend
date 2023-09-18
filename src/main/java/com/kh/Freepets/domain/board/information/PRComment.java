package com.kh.Freepets.domain.board.information;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "PR_COMMENT")
@NoArgsConstructor
@AllArgsConstructor

public class PRComment {

    @Id
    @Column(name = "pr_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "prCommentSequence")
    @SequenceGenerator(name = "prCommentSequence", sequenceName = "SEQ_PR_COMMENT", allocationSize = 1)
    private int prCommentCode;

    @Column(name = "pr_comment_date")
    private Date prCommentDate;

    @Column(name = "pr_comment_desc")
    private String prCommentDesc;

    @Column(name = "pr_comment_img")
    private String prCommentImg;

    @Column(name = "super_pr_comment_code")
    private int superPrCommentCode;

    @Column(name = "pr_comment_report_yn")
    private char prCommentReportYn;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_review_code")
    private ProductReview productReview;
}
