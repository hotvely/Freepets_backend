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
@Table(name = "LOST_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class LostComment {
    @Id
    @Column(name="L_COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "lostCommentSequence")
    @SequenceGenerator(name = "lostCommentSequence", sequenceName = "SEQ_LOST_COMMENT",allocationSize = 1)
    private int lostCommentCode;

    @Column(name="L_COMMENT_DESC")
    private String lostCommentDesc;
    @Column(name="L_COMMENT_DATE")
    private Date lostCommentDate;
    @Column(name="L_COMMENT_CODE_SUPER")
    private int lostCommentCodeSuper;
//    @Column(name="L_COMMENT_ADD_FILE_URL")
//    private String lostCommentAddFileUrl;


    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name="LOST_CODE")
    private Lost lost;

}
