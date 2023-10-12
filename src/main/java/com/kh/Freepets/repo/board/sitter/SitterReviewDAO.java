package com.kh.Freepets.repo.board.sitter;

import com.kh.Freepets.domain.board.sitter.SitterReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Vector;

public interface SitterReviewDAO extends JpaRepository<SitterReview, Integer> {
}
