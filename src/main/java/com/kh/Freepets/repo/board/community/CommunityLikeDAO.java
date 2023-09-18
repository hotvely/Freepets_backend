package com.kh.Freepets.repo.board.community;

import com.kh.Freepets.domain.board.community.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeDAO extends JpaRepository<CommunityLike, Integer> {
}
