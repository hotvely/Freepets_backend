package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.domain.board.community.QCommunity;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.community.CommunityDAO;
import com.kh.Freepets.repo.board.community.CommunityLikeDAO;
import com.kh.Freepets.service.member.MemberService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
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
public class CommunityService {
    @Autowired
    private CommunityDAO commonDAO;
    @Autowired
    private CommunityLikeDAO commonLikeDAO;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JPAQueryFactory queryFactory;
    private QCommunity qCommunity = QCommunity.community;

    public Page<Community> commonAll(Pageable pageable) {
        return commonDAO.findAll(pageable);
    }

    public Community showCommon(int id) {
        return commonDAO.findById(id).orElse(null);
    }

    public List<Community> findByMemberId(String id) {
        return commonDAO.findByMemberId(id);
    }

    public Community create(Community common) {
        return commonDAO.save(common);
    }

    public Community update(Community common) {
        Community target = commonDAO.findById(common.getCommonCode()).orElse(null);
        if (target != null) {
            return commonDAO.save(common);
        }
        return null;
    }

    public Community delete(int commonCode) {
        Community target = commonDAO.findById(commonCode).orElse(null);
        commonDAO.delete(target);
        return target;
    }

    // 게시글 좋아요 및 좋아요 해제
    // 게시글 좋아요 총 개수 증가
    public Community updateCommonLike(int commonCode) {
        commonDAO.updateCommonLike(commonCode);
        return commonDAO.findById(commonCode).orElse(null);

    }

    public Community deleteCommonLike(int commonCode) {
        commonDAO.deleteCommonLike(commonCode);
        return commonDAO.findById(commonCode).orElse(null);
    }

    // 검색 기능
    public Page<Community> searchKeyword(String keyword, int keywordType, Pageable pageable) {
        JPAQuery result;
        BooleanBuilder builder = new BooleanBuilder();
        switch (keywordType) {
            case 1:
                builder.or(qCommunity.commonTitle.contains(keyword));
                builder.or(qCommunity.commonDesc.contains(keyword));
                result = queryFactory.selectFrom(qCommunity).where(builder)
                        .orderBy(qCommunity.commonCode.desc());
                break;
            case 2:
                result = queryFactory.selectFrom(qCommunity).where(qCommunity.commonTitle.contains(keyword))
                        .orderBy(qCommunity.commonCode.desc());
                break;
            case 3:
                result = queryFactory.selectFrom(qCommunity).where(qCommunity.commonTitle.contains(keyword))
                        .orderBy(qCommunity.commonCode.desc());
                break;

            default:
                result = null;
                break;
        }
        log.info("keyword"+ keyword);
        log.info("keywordType"+ keywordType);
//        log.info("orderBy"+orderBy);

        long totalCount = result.fetchCount();
        result.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Community> resultList = result.fetch();

        return new PageImpl<>(resultList, pageable, totalCount);
    }


//        if (orderBy == 2) {
//            result.orderBy(qCommunity.commonLikeCount.desc());
//        } else if (orderBy == 3) {
//            result.orderBy(qCommunity.commonCommentCount.desc());
//        } else if (orderBy == 4) {
//            result.orderBy(qCommunity.commonViewCount.desc());
//        } else {
//            result.orderBy(qCommunity.commonCode.desc());
//        }
//
// 게시글 좋아요 총 개수 증가
//    public Community updateCommonLike(int commonCode) {
//        commonDAO.updateCommonLike(commonCode);
//        return commonDAO.findById(commonCode).orElse(null);
//
//    }
//
//    public Community deleteCommonLike(int commonCode) {
//        commonDAO.deleteCommonLike(commonCode);
//        return commonDAO.findById(commonCode).orElse(null);
//    }



}
