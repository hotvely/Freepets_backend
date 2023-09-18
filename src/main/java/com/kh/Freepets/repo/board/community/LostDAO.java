package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.community.LostComment;
import com.kh.Freepets.domain.board.community.LostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.management.ValueExp;
import java.util.List;


public interface LostDAO extends JpaRepository<Lost,Integer > {
    // 특정 유저의 분실 게시물 조회 // SELECT * FROM LOST WHERE ID=?
    @Query(value="SELECT * FROM LOST WHERE ID= :id", nativeQuery = true)
    List<Lost> findByMemberId(String id);


    // 게시글 좋아요 갯수 업데이트
    @Query(value = "UPDATE LOST SET LOST_LIKE =(LOST_LIKE +1) WHERE LOST_CODE = :lostCode",nativeQuery = true)
    Lost updateLostlike(int lostCode);

    // 게시글 좋아요 갯수 차감
    @Query(value = "UPDATE LOST SET LOST_LIKE =(LOST_LIKE -1) WHERE LOST_CODE = :lostCode",nativeQuery = true)
    Lost deleteLostlike(int lostCode);

    // 게시글 댓글 갯수 업데이트
    @Query(value = "UPDATE LOST SET LOST_COMMENT_COUNT =(LOST_COMMENT_COUNT +1) WHERE LOST_CODE = :lostCode",nativeQuery = true)
    Lost updateLostCommentCount(int lostCode);

    // 게시글 댓글 갯수 차감
    @Query(value = "UPDATE LOST SET LOST_COMMENT_COUNT =(LOST_COMMENT_COUNT -1) WHERE LOST_CODE = :lostCode",nativeQuery = true)
    Lost deleteLostCommentCount(int lostCode);
// ----------------------------------------------------------------------------------------------------------
    // 조회수별 게시물 정렬
    @Query(value="SELECT * FROM LOST ORDER BY LOST_VIEWS DESC", nativeQuery = true)
    List<Lost> sortLostViews();

    // 댓글수 별 게시글 정렬
    @Query(value = "SELECT * FROM LOST ORDER BY LOST_COMMENT_COUNT DESC", nativeQuery = true)
    List<Lost> sortCommentCount();
    // 좋아요 수 별 게시물 정렬
    @Query(value = "SELECT * FROM LOST ORDER BY LOST_LIKE DESC ", nativeQuery = true)
    List<Lost> sortLostLike();


}
