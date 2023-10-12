package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityLikeDAO extends JpaRepository<CommunityLike, Integer> {
    // 좋아요 중복 체크
    @Query(value = "SELECT * FROM COMMON_LIKE WHERE id = :id AND COMMON_CODE = :commonCode", nativeQuery = true)
    CommunityLike commonLikesByMemberAndCommunity(String id, int commonCode);
}
