package com.kh.Freepets.domain.board.community;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class LostLike {

    @Id
    @Column(name="LOST_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "lostlikeSequence")
    @SequenceGenerator(name="lostlikeSequence", sequenceName = "SEQ_LIKE_LOST", allocationSize=1)
   private int lostLikeCode;


    @ManyToOne
    @JoinColumn(name="LOST_CODE")
   private Lost lost;

    @ManyToOne
    @JoinColumn(name="ID")
   private Member member;


}
