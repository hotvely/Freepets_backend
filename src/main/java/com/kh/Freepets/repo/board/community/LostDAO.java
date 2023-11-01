package com.kh.Freepets.repo.board.community;


import com.kh.Freepets.domain.board.community.Lost;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LostDAO extends JpaRepository<Lost, Integer> {
    // 특정 멤버의 모든 일반 게시글 조회
    @Query(value = "SELECT * FROM LOST WHERE ID= :id", nativeQuery = true)
    List<Lost> findByMemberId(String id);

    // 게시글 좋아요 총 개수 증가
    @Transactional
    @Modifying
    @Query(value = "UPDATE LOST SET LOST_LIKE_COUNT = (LOST_LIKE_COUNT + 1) WHERE LOST_CODE = :lostCode", nativeQuery = true)
    int updateLostLike(int lostCode);

    // 게시글 좋아요 총 개수 감소
    @Transactional
    @Modifying
    @Query(value = "UPDATE LOST SET LOST_LIKE_COUNT = (LOST_LIKE_COUNT - 1) WHERE LOST_CODE = :lostCode", nativeQuery = true)
    int deleteLostLike(int lostCode);

    // 댓글 총 개수 증가
    @Transactional
    @Modifying
    @Query(value = "UPDATE LOST SET LOST_COMMENT_COUNT = (LOST_COMMENT_COUNT + 1 ) WHERE LOST_CODE = :lostCode", nativeQuery = true)
    int updateLostComments(int lostCode);

    // 댓글 총 개수 감소
    @Transactional
    @Modifying
    @Query(value = "UPDATE LOST SET LOST_COMMENT_COUNT = (LOST_COMMENT_COUNT + -1 ) WHERE LOST_CODE = :lostCode", nativeQuery = true)
    int deleteLostComments(int lostCode);

    // 조회수 증가
    @Transactional
    @Modifying
    @Query(value = "UPDATE LOST SET LOST_VIEW_COUNT = (LOST_VIEW_COUNT + 1 ) WHERE LOST_CODE = :lostCode", nativeQuery = true)
    int updateViews(int lostCode);

    // 댓글 검색
    @Transactional
    @Query(value = "SELECT * FROM LOST WHERE LOST_TITLE LIKE %:keyword% OR LOST_DESC LIKE %:keyword%", nativeQuery = true)
    Page<Lost> searchKeywordAll(String keyword, Pageable pageable);

    @Transactional
    @Query(value = "SELECT * FROM LOST WHERE LOST_TITLE LIKE %:keyword%", nativeQuery = true)
    Page<Lost> searchTitle(String keyword, Pageable pageable);

    @Transactional
    @Query(value = "SELECT * FROM LOST WHERE LOST_DESC LIKE %:keyword%", nativeQuery = true)
    Page<Lost> searchDesc(String keyword, Pageable pageable);



}


