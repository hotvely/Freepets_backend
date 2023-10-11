package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityDAO extends JpaRepository<Community, Integer> {
    // 특정 멤버의 모든 일반 게시글 조회
    @Query(value="SELECT * FROM COMMON WHERE ID= :id", nativeQuery = true)
    List<Community> findByMemberId(String id);

    // 게시글 좋아요 총 개수 증가
    @Query(value = "UPDATE COMMON SET COMMON_LIKE_COUNT = (COMMON_LIKE_COUNT + 1) WHERE COMMON_CODE = :commonCode")
    Community increaseCommonLikes(int commonCode);

    // 게시글 좋아요 총 개수 감소
    @Query(value = "UPDATE COMMON SET COMMON_LIKE_COUNT = (COMMON_LIKE_COUNT - 1) WHERE COMMON_CODE = :commonCode")
    Community decreaseCommonLikes(int commonCode);

    // 댓글 총 개수 증가
    @Query(value = "UPDATE COMMON SET COMMON_COMMENT_COUNT = (COMMON_COMMENT_COUNT + 1 ) WHERE COMMON_CODE = :commonCode")
    Community increaseCommonComments(int commonCode);

    // 댓글 총 개수 감소
    @Query(value = "UPDATE COMMON SET COMMON_COMMENT_COUNT = (COMMON_COMMENT_COUNT + -1 ) WHERE COMMON_CODE = :commonCode")
    Community decreaseCommonComments(int commonCode);

    // 게시글 정렬
    // 조회순
    @Query(value = "SELECT * FROM COMMON ORDER BY COMMON_VIEW_COUNT DESC")
    List<Community> sortCommonViews();

    // 추천순
    @Query(value = "SELECT * FROM COMMON ORDER BY COMMON_LIKE_COUNT DESC")
    List<Community> sortCommonLikes();

    // 댓글순
    @Query(value = "SELECT * FROM COMMON ORDER BY COMMON_COMMENT_COUNT DESC")
    List<Community> sortCommonComments();

}
