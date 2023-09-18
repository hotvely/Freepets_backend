package com.kh.Freepets.domain.board.information;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoInfo {

    @Id
    @Column(name = "video_info_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "videoInfoSequence")
    @SequenceGenerator(name = "videoInfoSequence", sequenceName = "SEQ_VIDEO_INFO", allocationSize = 1)
    private int videoInfoCode;

    @Column(name = "video_info_title")
    private String videoInfoTitle;

    @Column(name = "video_info_views")
    private int videoInfoViews;

    @Column(name = "video_info_date")
    private Date videoInfoDate;

    @Column(name = "video_info_file_url")
    private String videoInfoFileUrl;

    @Column(name = "video_info_url")
    private String videoInfoUrl;

    @Column(name = "video_info_kind")
    private String videoInfoKind;

    @Column(name = "video_info_like")
    private int videoInfoLike;

    @Column(name = "video_info_comment_count")
    private int videoInfoCommentCount;

    @Column(name = "video_info_report_yn")
    private char videoInfoReportYn;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}

