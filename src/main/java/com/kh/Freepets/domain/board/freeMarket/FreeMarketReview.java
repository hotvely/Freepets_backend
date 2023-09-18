package com.kh.Freepets.domain.board.freeMarket;

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
@Table(name = "FREEMARKET_REVIEW")
public class FreeMarketReview {
    @Id
    @Column(name = "FREEMARKET_REVIEW_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "freeMarketReviewSequence")
    @SequenceGenerator(name = "freeMarketReviewSequence", sequenceName = "SEQ_FREEMARKET_REVIEW" ,allocationSize = 1)
    private int freeMarketReviewCode;

    @Column(name = "FREEMARKET_REVIEW_DESC")
    private String freeMarketReviewDesc;

    @Column(name = "FREEMARKET_REVIEW_RATINGS")
    private int freeMarketReviewRatings;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "FREEMARKET_CODE")
    private FreeMarket freeMarket;
}
