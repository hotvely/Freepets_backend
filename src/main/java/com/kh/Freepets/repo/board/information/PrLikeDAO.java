package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.PrLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrLikeDAO extends JpaRepository<PrLike, Integer> {

    @Query(value = "SELECT * FROM pr_like WHERE id = :id AND product_review_code = :productReviewCode", nativeQuery = true)
    PrLike likeMember(String id, int productReviewCode);
}
