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
public class HrLike {

    @Id
    @Column(name = "hr_like_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "hrLikeSequence")
    @SequenceGenerator(name = "hrLikeSequence", sequenceName = "SEQ_HR_LIKE", allocationSize = 1)
    private int hrLikeCode;

    @ManyToOne
    @JoinColumn(name = "hospital_review_code")
    private HospitalReview hospitalReview;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}
