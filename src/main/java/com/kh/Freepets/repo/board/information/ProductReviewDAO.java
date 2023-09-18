package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.information.ProductReview;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductReviewDAO extends JpaRepository<ProductReview, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE PRODUCT_REVIEW SET PRODUCT_REVIEW_LIKE = (PRODUCT_REVIEW_LIKE + 1) WHERE PRODUCT_REVIEW_CODE = :productReviewCode", nativeQuery = true)
    int updateLike(int productReviewCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PRODUCT_REVIEW SET PRODUCT_REVIEW_LIKE = (PRODUCT_REVIEW_LIKE - 1) WHERE PRODUCT_REVIEW_CODE = :productReviewCode", nativeQuery = true)
    int deleteLike(int productReviewCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PRODUCT_REVIEW SET PRODUCT_REVIEW_VIEWS = (PRODUCT_REVIEW_VIEWS + 1) WHERE PRODUCT_REVIEW_CODE = :productReviewCode", nativeQuery = true)
    int updateViews(int productReviewCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PRODUCT_REVIEW SET PRODUCT_REVIEW_COMMENT_COUNT = (PRODUCT_REVIEW_COMMENT_COUNT + 1) WHERE PRODUCT_REVIEW_CODE = :productReviewCode", nativeQuery = true)
    int updateCommentCount(int productReviewCode);

    @Query(value = "SELECT * FROM PRODUCT_REVIEW ORDER BY PRODUCT_REVIEW_LIKE DESC", nativeQuery = true)
    List<ProductReview> showLike();

    @Query(value = "SELECT * FROM PRODUCT_REVIEW ORDER BY PRODUCT_REVIEW_COMMENT_COUNT DESC", nativeQuery = true)
    List<ProductReview> showComment();
}
