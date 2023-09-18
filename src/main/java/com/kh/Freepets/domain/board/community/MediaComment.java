package com.kh.Freepets.domain.board.community;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class MediaComment {
    @Id
    @Column(name="M_COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "mediaCommentSequence")
    @SequenceGenerator(name = "mediaCommentSequence",sequenceName = "SEQ_MEDIA_COMMENT",allocationSize = 1)
    private int mediaCommentCode;

    @Column(name = "M_COMMENT_DESC")
    private String mediaCommentDesc;
    @Column(name = "M_COMMENT_DATE")
    private Date mediaCommentDate;
    @Column(name = "M_COMMENT_CODE_SUPER")
    private String mediaCommentCodeSuper;
    @Column(name = "M_COMMENT_ADD_FILE_URL")
    private String mediaCommentAddFileUrl;

    // 댓글 신고

    @ManyToOne
    @JoinColumn(name = "MEDIA_CODE")
    private Media midea;
    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
    //private Report report;
}
