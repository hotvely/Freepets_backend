package com.kh.Freepets.domain.board.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
@Data
@Builder
@Entity
@Table(name = "COMMON_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class CommunityComment {
    @Id
    @Column(name="C_COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commonCommentSequence")
    @SequenceGenerator(name = "commonCommentSequence", sequenceName = "SEQ_COMMON_COMMENT",allocationSize = 1)
    private int commonCommentCode;

    @Column(name="C_COMMENT_DESC")
    private String commonCommentDesc;
    @Column(name="C_COMMENT_DATE")
    private Date commonCommentDate;
    @Column(name="C_COMMENT_CODE_SUPER")
    private int commonCommentCodeSuper;
    @Column(name="C_COMMENT_ADD_FILE_URL")
    private String commonCommentAddFileUrl;


    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name="COMMON_CODE")
    private Community community;

}
