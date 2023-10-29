package com.kh.Freepets.domain.board.community;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "LOST_LIKE")
@NoArgsConstructor
@AllArgsConstructor
public class LostLike {
    @Id
    @Column(name="LOST_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "lostLikeSequence" )
    @SequenceGenerator(name = "lostLikeSequence",sequenceName = "SEQ_LOST_LIKE" ,allocationSize = 1)
    private int lostLikeCode;

    @ManyToOne
    @JoinColumn(name="LOST_CODE")
    private Lost lost;
    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;

}
