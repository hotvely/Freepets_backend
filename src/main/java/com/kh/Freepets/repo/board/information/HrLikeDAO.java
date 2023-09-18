package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HrLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HrLikeDAO extends JpaRepository<HrLike, Integer> {

    @Query(value = "SELECT * FROM hr_like WHERE id = :id AND hospital_review_code = :hospitalReviewCode", nativeQuery = true)
    HrLike likeMember(String id, int hospitalReviewCode);
}
