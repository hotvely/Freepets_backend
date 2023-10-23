package com.kh.Freepets.repo.board.notice;

import com.kh.Freepets.domain.board.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface NoticeDAO extends JpaRepository<Notice, Integer> {
    // 특정 유저의 분실 게시물 조회 // SELECT * FROM NOTICE WHERE ID=?
    @Query(value = "SELECT * FROM NOTICE WHERE ID= :id ORDER BY NOTICE_CODE DESC", nativeQuery = true)
    List<Notice> findByMemberId(String id);

    @Query(value = "SELECT * FROM NOTICE WHERE NOTICE_TITLE LIKE %:keyword% OR NOTICE_DESC LIKE %:keyword% ORDER BY NOTICE_CODE DESC", nativeQuery = true)
    Page<Notice> search(String keyword, Pageable pageable);

    // 게시글 좋아요 갯수 업데이트
    @Query(value = "UPDATE NOTICE SET NOTICE_LIKE =(NOTICE_LIKE +1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    Notice updateNoticelike(int noticeCode);

    // 게시글 좋아요 갯수 차감
    @Query(value = "UPDATE NOTICE SET NOTICE_LIKE =(NOTICE_LIKE -1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    Notice deleteNoticelike(int noticeCode);

    // 게시글 댓글 갯수 업데이트
    @Query(value = "UPDATE NOTICE SET NOTICE_COMMENT_COUNT =(NOTICE_COMMENT_COUNT +1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    Notice updateNoticeCommentCount(int noticeCode);

    // 게시글 댓글 갯수 차감
    @Query(value = "UPDATE NOTICE SET NOTICE_COMMENT_COUNT =(NOTICE_COMMENT_COUNT -1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    Notice deleteNoticeCommentCount(int noticeCode);

    // ----------------------------------------------------------------------------------------------------------
    // 조회수별 게시물 정렬
    @Query(value = "SELECT * FROM NOTICE ORDER BY NOTICE_VIEWS DESC", nativeQuery = true)
    List<Notice> sortNoticeViews();

    // 댓글수 별 게시글 정렬
    @Query(value = "SELECT * FROM NOTICE ORDER BY NOTICE_COMMENT_COUNT DESC", nativeQuery = true)
    List<Notice> sortCommentCount();

    // 좋아요 수 별 게시물 정렬
    @Query(value = "SELECT * FROM NOTICE ORDER BY NOTICE_LIKE DESC ", nativeQuery = true)
    List<Notice> sortNoticeLike();


}
