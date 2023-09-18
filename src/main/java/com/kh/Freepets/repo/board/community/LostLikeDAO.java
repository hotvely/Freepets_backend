package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.LostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LostLikeDAO extends JpaRepository<LostLike, Integer> {

    // 분실게시글  좋아요 중복 처리
    @Query(value = "SELECT * FROM LOSTLIKE WHERE ID = :id AND LOST_CODE= :lostCode", nativeQuery = true)
    LostLike noDoubleLike (String id, int lostCode);


}
