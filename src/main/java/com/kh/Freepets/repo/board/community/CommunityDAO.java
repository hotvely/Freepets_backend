package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityDAO extends JpaRepository<Community, Integer> {
    // 특정 멤버의 모든 일반 게시글 조회
    @Query(value="SELECT * FROM COMMUNITY WHERE ID= :id", nativeQuery = true)
    List<Community> findByMemberId(String id);
}
