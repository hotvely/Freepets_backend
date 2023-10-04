package com.kh.Freepets.repo.board.sitter;

import com.kh.Freepets.domain.board.sitter.SitterReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Vector;

public interface SitterReviewDAO extends JpaRepository<SitterReview, Integer> {

    @Query(value = "SELECT R.* FROM SITTER_REVIEW R JOIN SITTER S ON (R.SITTER_CODE = S.SITTER_CODE) WHERE S.ID = :id", nativeQuery = true)
    List<SitterReview> findBySitter(String id);



}
