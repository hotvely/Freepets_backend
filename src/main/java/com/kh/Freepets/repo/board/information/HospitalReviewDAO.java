package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalReviewDAO extends JpaRepository<HospitalReview, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE HOSPITAL_REVIEW SET HOSPITAL_REVIEW_LIKE = (HOSPITAL_REVIEW_LIKE + 1) WHERE HOSPITAL_REVIEW_CODE = :hospitalReviewCode", nativeQuery = true)
    int updateLike(int hospitalReviewCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE HOSPITAL_REVIEW SET HOSPITAL_REVIEW_LIKE = (HOSPITAL_REVIEW_LIKE - 1) WHERE HOSPITAL_REVIEW_CODE = :hospitalReviewCode", nativeQuery = true)
    int deleteLike(int hospitalReviewCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE HOSPITAL_REVIEW SET HOSPITAL_REVIEW_VIEWS = (HOSPITAL_REVIEW_VIEWS + 1) WHERE HOSPITAL_REVIEW_CODE = :hospitalReviewCode", nativeQuery = true)
    int updateViews(int hospitalReviewCode);

    @Query(value = "SELECT * FROM HOSPITAL_REVIEW ORDER BY HOSPITAL_REVIEW_LIKE DESC", nativeQuery = true)
    List<HospitalReview> showLike();

    @Transactional
    @Modifying
    @Query(value = "UPDATE HOSPITAL_REVIEW SET HOSPITAL_REVIEW_COMMENT_COUNT = (HOSPITAL_REVIEW_COMMENT_COUNT + 1) WHERE HOSPITAL_REVIEW_CODE = :hospitalReviewCode", nativeQuery = true)
    int updateCommentCount(int hospitalReviewCode);

    @Query(value = "SELECT * FROM HOSPITAL_REVIEW ORDER BY HOSPITAL_REVIEW_COMMENT_COUNT DESC", nativeQuery = true)
    List<HospitalReview> showComment();
}
