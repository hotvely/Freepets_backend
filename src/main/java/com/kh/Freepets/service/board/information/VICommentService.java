package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.PRComment;
import com.kh.Freepets.domain.board.information.VIComment;
import com.kh.Freepets.domain.board.information.VideoInfo;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.VICommentDAO;
import com.kh.Freepets.repo.board.information.VideoInfoDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VICommentService {

    @Autowired
    private VICommentDAO dao;
    @Autowired
    private VideoInfoDAO videoInfoDao;
    @Autowired
    private MemberDAO memberDao;

    public List<VIComment> showBoardAll(int videoInfoCode) {
        return dao.showBoardAll(videoInfoCode);
    }

    public VIComment show(int viCommentCode) {

        VIComment viComment = dao.findById(viCommentCode).orElse(null);
        Member member = memberDao.findById(viComment.getMember().getId()).orElse(null);
        viComment.setMember(member);

        return viComment;
    }

    public VIComment create(VIComment viComment) {
        // 댓글 작성할 때마다 게시글 총 댓글 수 증가
        int result = videoInfoDao.updateCommentCount(viComment.getVideoInfo().getVideoInfoCode());
        return dao.save(viComment);
    }

    public VIComment update(VIComment viComment) {
        VIComment target = dao.findById(viComment.getViCommentCode()).orElse(null);
        if(target != null) {
            return dao.save(viComment);
        }
        return null;
    }

    public VIComment delete(int viCommentCode) {

        VIComment viComment = dao.findById(viCommentCode).orElse(null);
        dao.delete(viComment);
        return viComment;
    }
}
