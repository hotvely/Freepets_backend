package com.kh.Freepets.repo.board.notice;

import com.kh.Freepets.domain.board.notice.Notice;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NoticeDAO extends JpaRepository<Notice, Integer>
{
    // 특정 유저의 분실 게시물 조회 // SELECT * FROM NOTICE WHERE ID=?
    @Query(value = "SELECT * FROM NOTICE WHERE ID= :id ORDER BY NOTICE_CODE DESC", nativeQuery = true)
    List<Notice> findByMemberId(String id);


    @Query(value = "SELECT * FROM NOTICE WHERE NOTICE_TITLE LIKE %:keyword% OR NOTICE_DESC LIKE %:keyword% ORDER BY NOTICE_CODE", nativeQuery = true)
    Page<Notice> searchTitleContent(String keyword, Pageable pageable);


    @Query(value = "SELECT * FROM NOTICE WHERE NOTICE_DESC LIKE %:keyword% ORDER BY NOTICE_CODE", nativeQuery = true)
    Page<Notice> searchTitle(String keyword, Pageable pageable);


    @Query(value = "SELECT * FROM NOTICE WHERE NOTICE_TITLE LIKE %:keyword% ORDER BY NOTICE_CODE", nativeQuery = true)
    Page<Notice> searchContent(String keyword, Pageable pageable);


    // 게시글 좋아요 갯수 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE NOTICE SET NOTICE_LIKE =(NOTICE_LIKE +1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    void updateNoticelike(int noticeCode);

    // 게시글 좋아요 갯수 차감
    @Transactional
    @Modifying
    @Query(value = "UPDATE NOTICE SET NOTICE_LIKE =(NOTICE_LIKE -1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    void deleteNoticelike(int noticeCode);

    // 게시글 댓글 갯수 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE NOTICE SET NOTICE_COMMENT_COUNT =(NOTICE_COMMENT_COUNT +1) WHERE NOTICE_CODE=:noticeCode", nativeQuery = true)
    void updateNoticeCommentCount(int noticeCode);

    // 게시글 댓글 갯수 차감
    @Transactional
    @Modifying
    @Query(value = "UPDATE NOTICE SET NOTICE_COMMENT_COUNT =(NOTICE_COMMENT_COUNT -1) WHERE NOTICE_CODE = :noticeCode", nativeQuery = true)
    Notice deleteNoticeCommentCount(int noticeCode);

}
