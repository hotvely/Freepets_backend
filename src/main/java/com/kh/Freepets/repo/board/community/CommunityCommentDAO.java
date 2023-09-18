package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityCommentDAO extends JpaRepository<CommunityComment, Integer> {
    // 일반 게시글에 1개에 따른 모든 댓글 조회
    @Query(value = "SELECT * FROM COMMON_COMMENT WHERE COMMON_CODE = :code", nativeQuery = true)
    List<CommunityComment> findByCommonCode(int code);
}
