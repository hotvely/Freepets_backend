package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.LostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LostLikeDAO extends JpaRepository<LostLike, Integer> {
    // 좋아요 중복 체크
    @Query(value = "SELECT * FROM LOST_LIKE WHERE id = :id AND LOST_CODE = :lostCode", nativeQuery = true)
    LostLike lostLikesByMemberAndLost(String id, int lostCode);
}
