package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.LostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LostCommentDAO extends JpaRepository<LostComment, Integer> {
    // 일반 게시글에 1개에 따른 모든 댓글 조회

    @Query(value = "SELECT * FROM LOST_COMMENT WHERE LOST_CODE = :lostCode", nativeQuery = true)
    List<LostComment> lostCommentAll(int lostCode);

    // 부모 댓글에 따른 자식 댓글 조회
    @Query(value = "SELECT * FROM LOST_COMMENT WHERE L_COMMENT_CODE_SUPER = :lostCommentCodeSuper ORDER BY lostCommentCode", nativeQuery = true)
    List<LostComment> lostReCommentAll(int lostCommentCodeSuper);

}
