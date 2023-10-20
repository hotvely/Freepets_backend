package com.kh.Freepets.service.board.sitter;

import com.kh.Freepets.domain.board.sitter.QSitter;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.sitter.SitterDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitterService {

    @Autowired(required = true)
    private JPAQueryFactory queryFactory;
    @Autowired
    private SitterDAO sitterDAO;
    @Autowired
    private MemberDAO memberDAO;

    private final QSitter qSitter = QSitter.sitter;

    public Page<Sitter> showall(Pageable pageable) {
        return sitterDAO.findAll(pageable);
    }

    public Sitter show(int id) {
        Sitter sitter = sitterDAO.findById(id).orElse(null);
        Member member = memberDAO.findById(sitter.getMember().getId()).orElse(null);
        sitter.setMember(member);
        return sitter;
    }
    public Sitter create(Sitter sitter) {
        return sitterDAO.save(sitter);
    }

    public Sitter update(Sitter sitter) {
        Sitter target = sitterDAO.findById(sitter.getSitterCode()).orElse(null);
        if(target!=null) {
            return sitterDAO.save(sitter);
        }
        return null;
    }

    public Sitter delete(int id) {
        Sitter freeMarket = show(id);
        sitterDAO.delete(freeMarket);
        return freeMarket;
    }

    public int updateRatings(String id) {
        return sitterDAO.updateRatings(id);
    }

    public String isSitter(String id) {
        return sitterDAO.isSitter(id);
    }

    public String ratingsCount(String id) {
        return sitterDAO.ratingsCount(id);
    }

    public Page<Sitter> sitterSearch(String keyword, Pageable pageable) {

        JPAQuery<Sitter> result = queryFactory.selectFrom(qSitter)
                .where(qSitter.member.nickname.contains(keyword))
                .orderBy(qSitter.sitterCode.desc());

        long totalCount = result.fetchCount();
        result.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Sitter> resultList = result.fetch();

        return new PageImpl<>(resultList, pageable, totalCount);
    }

}
