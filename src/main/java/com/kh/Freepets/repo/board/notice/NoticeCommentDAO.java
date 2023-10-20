package com.kh.Freepets.repo.board.notice;

import com.kh.Freepets.domain.board.notice.NoticeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeCommentDAO extends JpaRepository<NoticeComment, Integer>
{
    //  SELECT * FROM NOTICE_COMMENT WHERE NOTICE_CODE = ?
    @Query(value = "SELECT * FROM NOTICE_COMMENT WHERE NOTICE_CODE = :code ORDER BY NOTICE_COMMENT_CODE", nativeQuery = true)
    List<NoticeComment> findByNoticeCode(int code);

    @Query(value = "SELECT * FROM NOTICE_COMMENT WHERE NOTICE_COMMENT_CODE_SUPER = :pCode ORDER BY NOTICE_COMMENT_CODE", nativeQuery = true)
    List<NoticeComment> findByReComment(int pCode);

}
