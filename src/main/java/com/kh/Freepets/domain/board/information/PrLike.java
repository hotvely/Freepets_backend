package com.kh.Freepets.domain.board.information;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class PrLike {

    @Id
    @Column(name = "pr_like_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "prLikeSequence")
    @SequenceGenerator(name = "prLikeSequence", sequenceName = "SEQ_PR_LIKE", allocationSize = 1)
    private int prLikeCode;

    @ManyToOne
    @JoinColumn(name = "product_review_code")
    private ProductReview productReview;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

}
