package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.HRComment;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.HRCommentDAO;
import com.kh.Freepets.repo.board.information.HospitalReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HRCommentService {

    @Autowired
    private HRCommentDAO dao;

    @Autowired
    private HospitalReviewDAO hospitalReviewDAO;

    @Autowired
    private MemberDAO memberDao;

    public List<HRComment> showBoardAll(int hospitalReviewCode) {
        return dao.showBoardAll(hospitalReviewCode);
    }

    public HRComment show(int hrCommentCode) {

        HRComment hrComment = dao.findById(hrCommentCode).orElse(null);
        Member member = memberDao.findById(hrComment.getMember().getId()).orElse(null);
        hrComment.setMember(member);

        return hrComment;
    }

    public HRComment create(HRComment hrComment) {
        // 댓글 작성할 때마다 게시글 총 댓글 수 증가
        int result = hospitalReviewDAO.updateCommentCount(hrComment.getHospitalReview().getHospitalReviewCode());
        return dao.save(hrComment);
    }

    public HRComment update(HRComment hrComment) {
        HRComment target = dao.findById(hrComment.getHrCommentCode()).orElse(null);
        if(target != null) {
            return dao.save(hrComment);
        }
        return null;
    }

    public HRComment delete(int hrCommentCode) {

        HRComment hrComment = dao.findById(hrCommentCode).orElse(null);
        dao.delete(hrComment);
        return hrComment;
    }
}
