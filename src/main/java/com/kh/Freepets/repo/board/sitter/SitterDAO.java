package com.kh.Freepets.repo.board.sitter;

import com.kh.Freepets.domain.board.sitter.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterDAO extends JpaRepository<Sitter, Integer> {
}
