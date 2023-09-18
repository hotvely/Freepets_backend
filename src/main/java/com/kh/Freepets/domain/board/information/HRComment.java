package com.kh.Freepets.domain.board.information;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="HR_COMMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class HRComment {

    @Id
    @Column(name = "hr_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "hrCommentSequence")
    @SequenceGenerator(name = "hrCommentSequence", sequenceName = "SEQ_HR_COMMENT", allocationSize = 1)
    private int hrCommentCode;

    @Column(name = "hr_comment_date")
    private Date hrCommentDate;

    @Column(name = "hr_comment_desc")
    private String hrCommentDesc;

    @Column(name = "hr_comment_img")
    private String hrCommentImg;

    @Column(name = "super_hr_comment_code")
    private int superHrCommentCode;

    @Column(name = "hr_comment_report_yn")
    private char hrCommentReportYn;


    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "hospital_review_code")
    private HospitalReview hospitalReview;


}
