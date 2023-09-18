package com.kh.Freepets.repo.board.information;

import com.kh.Freepets.domain.board.information.HRComment;
import com.kh.Freepets.domain.board.information.VIComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VICommentDAO extends JpaRepository<VIComment, Integer> {
    @Query(value = "SELECT * FROM VI_COMMENT WHERE VIDEO_INFO_CODE = :videoInfoCode", nativeQuery = true)
    List<VIComment> showBoardAll(int videoInfoCode);

}
