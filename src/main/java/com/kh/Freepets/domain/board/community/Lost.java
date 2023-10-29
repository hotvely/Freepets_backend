package com.kh.Freepets.domain.board.community;

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
@Table(name = "LOST")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Lost {
    @Id
    @Column(name = "LOST_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "lostSequence")
    @SequenceGenerator(name = "lostSequence", sequenceName = "SEQ_LOST", allocationSize = 1)
    private int lostCode;
    @Column(name = "LOST_DATE")
    private Date lostDate;
    @Column(name = "LOST_TITLE")
    private String lostTitle;
    @Column(name = "LOST_DESC")
    private String lostDesc;
    @Column(name = "LOST_ADDFILE_URL")
    private String lostAddFileUrl;
    @Column(name = "LOST_YOUTUBE_URL")
    private String youtubeUrl;

    //정렬 위한
    //조회수
    @Column(name = "LOST_VIEW_COUNT")
    private int lostViewCount;
    //추천수
    //@ColumnDefault("0")
    @Column(name = "LOST_LIKE_COUNT")
    private int lostLikeCount;
    //댓글수
    @Column(name = "LOST_COMMENT_COUNT")
    private int lostCommentCount;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;

}
