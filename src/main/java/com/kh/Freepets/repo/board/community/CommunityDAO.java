package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.Community;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityDAO extends JpaRepository<Community, Integer> {
    // 특정 멤버의 모든 일반 게시글 조회
    @Query(value="SELECT * FROM COMMON WHERE ID= :id", nativeQuery = true)
    List<Community> findByMemberId(String id);

    // 게시글 좋아요 총 개수 증가
    @Transactional
    @Modifying
    @Query(value = "UPDATE COMMON SET COMMON_LIKE_COUNT = (COMMON_LIKE_COUNT + 1) WHERE COMMON_CODE = :commonCode",nativeQuery = true)
    int increaseCommonLikes(int commonCode);

    // 게시글 좋아요 총 개수 감소
    @Transactional
    @Modifying
    @Query(value = "UPDATE COMMON SET COMMON_LIKE_COUNT = (COMMON_LIKE_COUNT - 1) WHERE COMMON_CODE = :commonCode", nativeQuery = true)
    int decreaseCommonLikes(int commonCode);

    // 댓글 총 개수 증가
    @Transactional
    @Modifying
    @Query(value = "UPDATE COMMON SET COMMON_COMMENT_COUNT = (COMMON_COMMENT_COUNT + 1 ) WHERE COMMON_CODE = :commonCode", nativeQuery = true)
    int increaseCommonComments(int commonCode);

    // 댓글 총 개수 감소
    @Transactional
    @Modifying
    @Query(value = "UPDATE COMMON SET COMMON_COMMENT_COUNT = (COMMON_COMMENT_COUNT + -1 ) WHERE COMMON_CODE = :commonCode", nativeQuery = true)
    int decreaseCommonComments(int commonCode);

    // 조회수 증가
    @Transactional
    @Modifying
    @Query(value= "UPDATE COMMON SET COMMON_VIEW_COUNT = (COMMON_VIEW_COUNT + 1 ) WHERE COMMON_CODE = :commonCode", nativeQuery = true)
    int increaseViews (int commonCode);

    // 키워드 검색
    @Transactional
    @Modifying
    @Query(value="SELECT * FROM COMMON WHERE COMMON_TITLE LIKE %:keyword% OR COMMON_DESC LIKE %:keyword% ORDER BY COMMON_CODE DESC" , nativeQuery = true)
    Page<Community> searchTitleAndDesc(@Param("keyword") String keyword, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="SELECT * FROM COMMON WHERE COMMON_TITLE LIKE %:keyword% ORDER BY COMMON_CODE DESC" , nativeQuery = true)
    Page<Community> searchTitle(@Param("keyword") String keyword, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="SELECT * FROM COMMON WHERE COMMON_DESC LIKE %:keyword% ORDER BY COMMON_CODE DESC" , nativeQuery = true)
    Page<Community> searchDesc(@Param("keyword") String keyword, Pageable pageable);
}

//    // 조회순
//    @Query(value = "SELECT * FROM COMMON ORDER BY COMMON_VIEW_COUNT DESC", nativeQuery = true)
//    List<Community> sortCommonViews();
//
//    // 추천순
//    @Query(value = "SELECT * FROM COMMON ORDER BY COMMON_LIKE_COUNT DESC", nativeQuery = true)
//    List<Community> sortCommonLikes();
//
//    // 댓글순
//    @Query(value = "SELECT * FROM COMMON ORDER BY COMMON_COMMENT_COUNT DESC", nativeQuery = true)
//    List<Community> sortCommonComments();


