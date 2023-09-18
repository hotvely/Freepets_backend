package com.kh.Freepets.domain.board.community;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLike {
    @Id
    @Column(name="C_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commonLikeSequence" )
    @SequenceGenerator(name = "commonLikeSequence",sequenceName = "SEQ_COMMON_LIKE" ,allocationSize = 1)
    private int commonLikeCode;

    @ManyToOne
    @JoinColumn(name="COMMON_CODE")
    private Community community;
    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
}
