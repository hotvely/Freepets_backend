package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.information.ProductReview;
import com.kh.Freepets.domain.board.information.VideoInfo;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.VideoInfoDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoInfoService {

    @Autowired
    private VideoInfoDAO videoInfoDAO;

    @Autowired
    private MemberDAO memberDAO;

    // 모든 게시글 보기
    public List<VideoInfo> showAll() {
        return videoInfoDAO.findAll();
    }

    // 게시글 한 개 보기
    public VideoInfo show(int videoInfoCode) {

        // 게시글 들어갈 때마다 조회수 올라가기
        videoInfoDAO.updateViews(videoInfoCode);

        VideoInfo videoInfo = videoInfoDAO.findById(videoInfoCode).orElse(null);
        Member member = memberDAO.findById(videoInfo.getMember().getId()).orElse(null);
        videoInfo.setMember(member);
        return videoInfo;
    }

    // 게시글 작성하기
    public VideoInfo create(VideoInfo videoInfo) {
        return videoInfoDAO.save(videoInfo);
    }

    // 게시글 수정하기
    public VideoInfo update(VideoInfo videoInfo) {
        VideoInfo target = videoInfoDAO.findById(videoInfo.getVideoInfoCode()).orElse(null);
        if (target != null) {
            videoInfoDAO.save(videoInfo);
        }
        return null;
    }

    // 게시글 삭제하기
    public VideoInfo delete(int videoInfoCode) {
        VideoInfo videoInfo = videoInfoDAO.findById(videoInfoCode).orElse(null);
        videoInfoDAO.delete(videoInfo);
        return videoInfo;
    }

    // 게시글 좋아요 하기
    public VideoInfo updateLike(int videoInfoCode) {
        videoInfoDAO.updateLike(videoInfoCode);
        return videoInfoDAO.findById(videoInfoCode).orElse(null);
    }

    // 게시글 좋아요 취소
    public VideoInfo deleteLike(int videoInfoCode) {
        videoInfoDAO.deleteLike(videoInfoCode);
        return videoInfoDAO.findById(videoInfoCode).orElse(null);
    }

    // 좋아요 수 정렬 게시글 보기
    public List<VideoInfo> showLike() {
        return videoInfoDAO.showLike();
    }

    // 댓글 수 정렬 게시글 보기
    public List<VideoInfo> showComment(){
        return videoInfoDAO.showComment();
    }
}
