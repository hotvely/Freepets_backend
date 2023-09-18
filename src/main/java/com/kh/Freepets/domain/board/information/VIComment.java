package com.kh.Freepets.domain.board.information;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "vi_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VIComment {

    @Id
    @Column(name = "vi_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "viCommentSequence")
    @SequenceGenerator(name = "viCommentSequence", sequenceName = "SEQ_VI_COMMENT", allocationSize = 1)
    private int viCommentCode;

    @Column(name = "vi_comment_date")
    private Date viCommentDate;

    @Column(name = "vi_comment_desc")
    private String viCommentDesc;

    @Column(name = "vi_comment_img")
    private String viCommentImg;

    @Column(name = "super_vi_comment_code")
    private int superViCommentCode;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "video_info_code")
    private VideoInfo videoInfo;
}
