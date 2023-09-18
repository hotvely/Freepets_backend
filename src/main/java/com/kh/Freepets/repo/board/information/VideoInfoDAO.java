package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.information.VideoInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoInfoDAO extends JpaRepository<VideoInfo, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE VIDEO_INFO SET VIDEO_INFO_LIKE = (VIDEO_INFO_LIKE + 1) WHERE VIDEO_INFO_CODE = :videoInfoCode", nativeQuery = true)
    int updateLike(int videoInfoCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE VIDEO_INFO SET VIDEO_INFO_LIKE = (VIDEO_INFO_LIKE - 1) WHERE VIDEO_INFO_CODE = :videoInfoCode", nativeQuery = true)
    int deleteLike(int videoInfoCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE VIDEO_INFO SET VIDEO_INFO_VIEWS = (VIDEO_INFO_VIEWS + 1) WHERE VIDEO_INFO_CODE = :videoInfoCode", nativeQuery = true)
    int updateViews(int videoInfoCode);

    @Query(value = "SELECT * FROM VIDEO_INFO ORDER BY VIDEO_INFO_LIKE DESC", nativeQuery = true)
    List<VideoInfo> showLike();

    @Transactional
    @Modifying
    @Query(value = "UPDATE VIDEO_INFO SET VIDEO_INFO_COMMENT_COUNT = (VIDEO_INFO_COMMENT_COUNT + 1) WHERE VIDEO_INFO_CODE = :videoInfoCode", nativeQuery = true)
    int updateCommentCount(int videoInfoCode);

    @Query(value = "SELECT * FROM VIDEO_INFO ORDER BY VIDEO_INFO_COMMENT_COUNT DESC", nativeQuery = true)
    List<VideoInfo> showComment();
}
