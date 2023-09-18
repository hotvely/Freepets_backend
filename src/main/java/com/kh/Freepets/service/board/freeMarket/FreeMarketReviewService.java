package com.kh.Freepets.service.board.freeMarket;

import com.kh.Freepets.domain.board.freeMarket.FreeMarket;
import com.kh.Freepets.domain.board.freeMarket.FreeMarketReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.freeMarket.FreeMarketDAO;
import com.kh.Freepets.repo.board.freeMarket.FreeMarketReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeMarketReviewService {

    @Autowired
    private FreeMarketReviewDAO freeMarketReviewDAO;

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private FreeMarketDAO freeMarketDAO;

    public List<FreeMarketReview> showall() {
        return freeMarketReviewDAO.findAll();
    }

    public FreeMarketReview show(int id) {
        FreeMarketReview freeMarketReview = freeMarketReviewDAO.findById(id).orElse(null);
        Member member = memberDAO.findById(freeMarketReview.getMember().getId()).orElse(null);
        FreeMarket freeMarket = freeMarketDAO.findById(freeMarketReview.getFreeMarket().getFreeMarketCode()).orElse(null);
        freeMarketReview.setFreeMarket(freeMarket);
        freeMarketReview.setMember(member);
        return freeMarketReview;
    }
    public FreeMarketReview create(FreeMarketReview freeMarketReview) {
        return freeMarketReviewDAO.save(freeMarketReview);
    }

    public FreeMarketReview update(FreeMarketReview freeMarketReview) {
        FreeMarketReview target = freeMarketReviewDAO.findById(freeMarketReview.getFreeMarketReviewCode()).orElse(null);
        if(target!=null) {
            return freeMarketReviewDAO.save(freeMarketReview);
        }
        return null;
    }

    public FreeMarketReview delete(int id) {
        FreeMarketReview freeMarketReview = show(id);
        freeMarketReviewDAO.delete(freeMarketReview);
        return freeMarketReview;
    }

}
