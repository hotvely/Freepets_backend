package com.kh.Freepets.domain.board.information;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class HospitalReview {
    @Id
    @Column(name = "hospital_review_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "hospitalReviewSequence")
    @SequenceGenerator(name = "hospitalReviewSequence", sequenceName = "SEQ_HOSPITAL_REVIEW", allocationSize = 1)
    private int hospitalReviewCode;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospital_address")
    private String hospitalAddress;

    @Column(name = "hospital_review_views")
    private int hospitalReviewViews;

    @Column(name = "hospital_review_date")
    private Date hospitalReviewDate;

    @Column(name = "hospital_review_title")
    private String hospitalReviewTitle;

    @Column(name = "hospital_review_desc")
    private String hospitalReviewDesc;

    @Column(name = "hospital_review_file_url")
    private String hospitalReviewFileUrl;

    @Column(name = "hospital_review_url")
    private String hospitalReviewUrl;

    @Column(name = "hospital_review_like")
    private int hospitalReviewLike;

    @Column(name = "hospital_review_comment_count")
    private int hospitalReviewCommentCount;

    @Column(name = "hospital_review_report_yn")
    private char hospitalReviewReportYn;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}
