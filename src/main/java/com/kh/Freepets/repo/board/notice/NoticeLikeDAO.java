package com.kh.Freepets.repo.board.notice;

import com.kh.Freepets.domain.board.notice.NoticeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NoticeLikeDAO extends JpaRepository<NoticeLike, Integer>
{

    // 분실게시글  좋아요 중복 처리
    @Query(value = "SELECT * FROM LOSTLIKE WHERE ID = :id AND LOST_CODE= :lostCode", nativeQuery = true)
    NoticeLike noDoubleLike(String id, int lostCode);

    @Query(value = "SELECT * FROM NOTICE_LIKE WHERE ID = :id AND NOTICE_CODE = :noticeCode", nativeQuery = true)
    NoticeLike findById(String id, int noticeCode);

}
