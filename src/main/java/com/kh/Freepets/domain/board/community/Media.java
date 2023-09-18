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
public class Media {

    @Id
    @Column(name="MEDIA_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "mediaSequence")
    @SequenceGenerator(name = "mediaSequence", sequenceName = "SEQ_MEDIA", allocationSize = 1)
    private int mediaCode;

    @Column(name="MEDIA_TITLE")
    private String mediaTitle;
    @Column(name="MEDIA_DATE")
    private Date mediaDate;
    @Column(name="MEDIA_VIEWS")
    private int mediaViews;
    @Column(name="MEDIA_ADD_FILE_URL")
    private String mediaAddFileUrl;
    @Column(name="MEDIA_YOUTUBE_URL")
    private String youtubeUrl;
    @Column(name="MEDIA_DESC")
    private String mediaDesc;
    @Column(name="MEDIA_PHOTO")
    private String mediaPhoto;
    @Column(name="MEDIA_CATEGORY")
    private String category;

    //좋아요 -> 개수 처리용
    //댓글 좋아요
    //신고

    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
    //private Report report;


}
