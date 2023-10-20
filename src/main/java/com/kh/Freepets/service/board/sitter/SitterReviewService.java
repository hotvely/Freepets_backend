package com.kh.Freepets.service.board.sitter;

import com.kh.Freepets.domain.board.sitter.QSitter;
import com.kh.Freepets.domain.board.sitter.QSitterReview;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.board.sitter.SitterReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.sitter.SitterDAO;
import com.kh.Freepets.repo.board.sitter.SitterReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Autowired(required = true)
    private JPAQueryFactory queryFactory;

    private final QSitterReview qSitterReview = QSitterReview.sitterReview;
    private final QSitter qSitter = QSitter.sitter;

    public List<SitterReview> showall(String id) {
        return queryFactory.selectFrom(qSitterReview)
                .join(qSitterReview.sitter, qSitter)
                .where(qSitter.member.id.eq(id))
                .orderBy(qSitterReview.sitterReviewCode.desc())
                .fetch();
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
