package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HRComment;
import com.kh.Freepets.domain.board.information.PRComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PRCommentDAO extends JpaRepository<PRComment, Integer> {

    @Query(value = "SELECT * FROM PR_COMMENT WHERE PRODUCT_REVIEW_CODE = :productReviewCode", nativeQuery = true)
    List<PRComment> showBoardAll(int productReviewCode);
}
