package com.kh.Freepets.service.board.sitter;

import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.board.sitter.SitterReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.sitter.SitterDAO;
import com.kh.Freepets.repo.board.sitter.SitterReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitterReviewService {

    @Autowired
    private SitterReviewDAO sitterReviewDAO;

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private SitterDAO sitterDAO;

    public List<SitterReview> showall() {
        return sitterReviewDAO.findAll();
    }

    public SitterReview show(int id) {
        SitterReview sitterReview = sitterReviewDAO.findById(id).orElse(null);
        Member member = memberDAO.findById(sitterReview.getMember().getId()).orElse(null);
        Sitter sitter = sitterDAO.findById(sitterReview.getSitter().getSitterCode()).orElse(null);
        sitterReview.setSitter(sitter);
        sitterReview.setMember(member);
        return sitterReview;
    }
    public SitterReview create(SitterReview sitterReview) {
        return sitterReviewDAO.save(sitterReview);
    }

    public SitterReview update(SitterReview sitterReview) {
        SitterReview target = sitterReviewDAO.findById(sitterReview.getSitterReviewCode()).orElse(null);
        if(target!=null) {
            return sitterReviewDAO.save(sitterReview);
        }
        return null;
    }

    public SitterReview delete(int id) {
        SitterReview sitterReview = show(id);
        sitterReviewDAO.delete(sitterReview);
        return sitterReview;
    }

}
