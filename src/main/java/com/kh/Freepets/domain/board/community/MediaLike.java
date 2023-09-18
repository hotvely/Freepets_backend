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
public class MediaLike {
    @Id
    @Column(name = "M_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator ="mediaLikeSequence" )
    @SequenceGenerator(name = "mediaLikeSequence" ,sequenceName = "SEQ_MEDIA_LIKE",allocationSize = 1)
    private int mediaLikeCode;

    @ManyToOne
    @JoinColumn(name = "MEDIA_CODE")
    private Media media;
    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
}
