package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HRComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HRCommentDAO extends JpaRepository<HRComment, Integer> {
    @Query(value = "SELECT * FROM HR_COMMENT WHERE HOSPITAL_REVIEW_CODE = :hospitalReviewCode", nativeQuery = true)
    List<HRComment> showBoardAll(int hospitalReviewCode);
}
