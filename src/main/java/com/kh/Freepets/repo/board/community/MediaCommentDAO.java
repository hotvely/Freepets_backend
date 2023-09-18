package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.MediaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaCommentDAO extends JpaRepository<MediaComment, Integer> {
    // 영상 게시글에 1개에 따른 모든 댓글 조회
    @Query(value = "SELECT * FROM MEDIA_COMMENT WHERE MEDIA_CODE = :code", nativeQuery = true)
    List<MediaComment> findByMediaCode(int code);
}
