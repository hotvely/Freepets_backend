package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityDAO extends JpaRepository<Community, Integer> {
    // 특정 멤버의 모든 일반 게시글 조회
    @Query(value="SELECT * FROM COMMUNITY WHERE ID= :id", nativeQuery = true)
    List<Community> findByMemberId(String id);

    // 게시글 좋아요 총 개수 추가
    @Query(value = "UPDATE COMMUNITY SET COMMON_LIKE_COUNT = (COMMON_LIKE_COUNT + 1) WHERE COMMON_CODE = :commonCode")

    // 게시글 좋아요 총 개수 삭제
}
