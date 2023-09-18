package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaDAO extends JpaRepository<Media, Integer> {
    // 특정 멤버의 모든 영상 게시글 조회
    @Query(value="SELECT * FROM MEDIA WHERE ID= :id", nativeQuery = true)
    List<Media> findByMemberId(String id);
}
