package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HrLike;
import com.kh.Freepets.domain.board.information.ViLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ViLikeDAO extends JpaRepository<ViLike, Integer> {

    @Query(value = "SELECT * FROM vi_like WHERE id = :id AND video_info_code = :videoInfoCode", nativeQuery = true)
    ViLike likeMember(String id, int videoInfoCode);
}
