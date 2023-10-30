package com.kh.Freepets.service.board;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.community.QCommunity;
import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.information.QHospitalReview;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.notice.QNotice;
import com.kh.Freepets.domain.board.sitter.QSitter;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.util.Paging;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalSearchService {

    @Autowired(required = true)
    private JPAQueryFactory queryFactory;

    private final QCommunity qCommunity = QCommunity.community;
    private final QSitter qSitter = QSitter.sitter;
    private final QHospitalReview qHospitalReview = QHospitalReview.hospitalReview;
    private final QNotice qNotice = QNotice.notice;

    public Paging getSearchPage(String search) {
        List<Community> communityList = queryFactory.selectFrom(qCommunity)
                .where(qCommunity.commonTitle.contains(search))
                .orderBy(qCommunity.commonCode.desc())
                .fetch();
        List<Sitter> sitterList = queryFactory.selectFrom(qSitter)
                .where(qSitter.sitterTitle.contains(search))
                .orderBy(qSitter.sitterCode.desc())
                .fetch();
        List<HospitalReview> hospitalReviewList = queryFactory.selectFrom(qHospitalReview)
                .where(qHospitalReview.hospitalReviewTitle.contains(search))
                .orderBy(qHospitalReview.hospitalReviewCode.desc())
                .fetch();
        List<Notice> noticeList = queryFactory.selectFrom(qNotice)
                .where(qNotice.noticeTitle.contains(search))
                .orderBy(qNotice.noticeCode.desc())
                .fetch();
        Paging paging = Paging.builder()
                .communityList(communityList)
                .sitterList(sitterList)
                .hospitalReviewList(hospitalReviewList)
                .noticeList(noticeList)
                .build();
        return paging;
    }
}
