package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.LostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LostCommentDAO extends JpaRepository<LostComment,Integer> {
   //  SELECT * FROM LOST_COMMENT WHERE LOST_CODE = ?
    @Query(value = "SELECT * FROM LOST_COMMENT WHERE LOST_CODE = :code", nativeQuery = true)
    List<LostComment> findByLostCode(int code);


}
