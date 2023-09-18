package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.PRComment;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.PRCommentDAO;
import com.kh.Freepets.repo.board.information.ProductReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PRCommentService {

    @Autowired
    private PRCommentDAO dao;
    @Autowired
    private ProductReviewDAO productReviewDAO;
    @Autowired
    private MemberDAO memberDao;

    public List<PRComment> showBoardAll(int productReviewCode) {
        return dao.showBoardAll(productReviewCode);
    }

    public PRComment show(int prCommentCode) {

        PRComment prComment = dao.findById(prCommentCode).orElse(null);
        Member member = memberDao.findById(prComment.getMember().getId()).orElse(null);
        prComment.setMember(member);

        return prComment;
    }

    public PRComment create(PRComment prComment) {
        // 댓글 작성할 때마다 게시글 총 댓글 수 증가
        int result = productReviewDAO.updateCommentCount(prComment.getProductReview().getProductReviewCode());
        return dao.save(prComment);
    }

    public PRComment update(PRComment prComment) {
        PRComment target = dao.findById(prComment.getPrCommentCode()).orElse(null);
        if(target != null) {
            return dao.save(prComment);
        }
        return null;
    }

    public PRComment delete(int prCommentCode) {

        PRComment prComment = dao.findById(prCommentCode).orElse(null);
        dao.delete(prComment);
        return prComment;
    }
}
