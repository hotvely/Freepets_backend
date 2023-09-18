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
public class Community {
    @Id
    @Column(name="COMMON_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commonSequence")
    @SequenceGenerator(name = "commonSequence", sequenceName = "SEQ_COMMON",allocationSize = 1)
    private int commonCode;
    @Column(name="COMMON_DATE")
    private Date commonDate;
    @Column(name="COMMON_VIEWS")
    private int commonViews;
    @Column(name="COMMON_TITLE")
    private String commonTitle;
    @Column(name="COMMON_DESC")
    private String commonDesc;
    @Column(name="COMMON_ADD_FILE_URL")
    private String commonAddFileUrl;
    @Column(name="COMMON_YOUTUBE_URL")
    private String youtubeUrl;

    //좋아요 -> 개수 처리용
    //댓글 좋아요
    //신고


    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
    ////private Report report; 신고 만들어줘

}
