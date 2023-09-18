package com.kh.Freepets.domain.board.sitter;

import com.kh.Freepets.domain.board.freeMarket.FreeMarket;
import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SITTER_REVIEW")
public class SitterReview {
    @Id
    @Column(name = "SITTER_REVIEW_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sitterReviewSequence")
    @SequenceGenerator(name = "sitterReviewSequence", sequenceName = "SEQ_SITTER_REVIEW" ,allocationSize = 1)
    private int sitterReviewCode;

    @Column(name = "SITTER_REVIEW_DESC")
    private String sitterReviewDesc;

    @Column(name = "SITTER_REVIEW_RATINGS")
    private int sitterReviewRatings;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "SITTER_CODE")
    private Sitter sitter;
}
