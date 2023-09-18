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
public class ViLike {

    @Id
    @Column(name = "vi_like_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "viLikeSequence")
    @SequenceGenerator(name = "viLikeSequence", sequenceName = "SEQ_VI_LIKE", allocationSize = 1)
    private int viLikeCode;

    @ManyToOne
    @JoinColumn(name = "video_info_code")
    private VideoInfo videoInfo;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}
