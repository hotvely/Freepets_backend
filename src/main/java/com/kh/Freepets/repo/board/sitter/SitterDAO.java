package com.kh.Freepets.repo.board.sitter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.Freepets.domain.board.sitter.Sitter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SitterDAO extends JpaRepository<Sitter, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE SITTER " +
            "SET SITTER_RATINGS = (SELECT ROUND(AVG(R.SITTER_REVIEW_RATINGS),1) " +
            "FROM SITTER_REVIEW R JOIN SITTER S ON (R.SITTER_CODE = S.SITTER_CODE) " +
            "WHERE S.ID = :id GROUP BY S.ID) " +
            "WHERE ID = :id", nativeQuery = true)
    int updateRatings(String id);

    @Query(value = "SELECT COUNT(*) FROM SITTER_REVIEW R JOIN SITTER S ON (S.SITTER_CODE = R.SITTER_CODE) WHERE S.ID = :id GROUP BY S.ID", nativeQuery = true)
    int ratingsCount(String id);

    @Query(value = "SELECT DISTINCT ID FROM SITTER WHERE ID = :id", nativeQuery = true)
    String isSitter(String id);
}
