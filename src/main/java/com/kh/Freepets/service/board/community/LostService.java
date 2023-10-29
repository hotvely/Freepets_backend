package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.community.QCommunity;
import com.kh.Freepets.repo.board.community.CommunityDAO;
import com.kh.Freepets.repo.board.community.LostDAO;
import com.kh.Freepets.repo.board.community.LostLikeDAO;
import com.kh.Freepets.service.member.MemberService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class LostService {
    @Autowired
    private LostDAO lostDAO;
    @Autowired
    private LostLikeDAO lostLikeDAO;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JPAQueryFactory queryFactory;

    public Page<Lost> lostAll(Pageable pageable) {
        return lostDAO.findAll(pageable);
    }

    public Lost showlost(int id) {
        return lostDAO.findById(id).orElse(null);
    }

    public List<Lost> findByMemberId(String id) {
        return lostDAO.findByMemberId(id);
    }

    public Lost create(Lost lost) {
        return lostDAO.save(lost);
    }

    public Lost update(Lost lost) {
        Lost target = lostDAO.findById(lost.getLostCode()).orElse(null);
        if (target != null) {
            return lostDAO.save(lost);
        }
        return null;
    }

    public Lost delete(int lostCode) {
        Lost target = lostDAO.findById(lostCode).orElse(null);
        lostDAO.delete(target);
        return target;
    }

    // 게시글 좋아요 및 좋아요 해제
    // 게시글 좋아요 총 개수 증가
    public Lost updateLostLike(int lostCode) {
        lostDAO.updateLostLike(lostCode);
        return lostDAO.findById(lostCode).orElse(null);

    }

    public Lost deleteLostLike(int lostCode) {
        lostDAO.deleteLostLike(lostCode);
        return lostDAO.findById(lostCode).orElse(null);
    }

    // 검색 기능 -> queryDSL Q클래스 업데이트 곤란...
//    public Page<Community> searchKeyword(String keyword, int keywordType, Pageable pageable) {
//        JPAQuery result;
//        BooleanBuilder builder = new BooleanBuilder();
//        switch (keywordType) {
//            case 1:
//                builder.or(qCommunity.commonTitle.contains(keyword));
//                builder.or(qCommunity.commonDesc.contains(keyword));
//                result = queryFactory.selectFrom(qCommunity).where(builder)
//                        .orderBy(qCommunity.commonCode.desc());
//                break;
//            case 2:
//                result = queryFactory.selectFrom(qCommunity).where(qCommunity.commonTitle.contains(keyword))
//                        .orderBy(qCommunity.commonCode.desc());
//                break;
//            case 3:
//                result = queryFactory.selectFrom(qCommunity).where(qCommunity.commonTitle.contains(keyword))
//                        .orderBy(qCommunity.commonCode.desc());
//                break;
//
//            default:
//                result = null;
//                break;
//        }
//        log.info("keyword"+ keyword);
//        log.info("keywordType"+ keywordType);
////        log.info("orderBy"+orderBy);
//
//        long totalCount = result.fetchCount();
//        result.offset(pageable.getOffset())
//                .limit(pageable.getPageSize());
//
//        List<Community> resultList = result.fetch();
//
//        return new PageImpl<>(resultList, pageable, totalCount);
//    }



}
