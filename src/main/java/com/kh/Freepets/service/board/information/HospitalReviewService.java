package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.information.QHospitalReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.HospitalReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalReviewService {

    @Autowired(required = true)
    private JPAQueryFactory queryFactory;
    @Autowired
    private HospitalReviewDAO hospitalReviewDAO;
    @Autowired
    private MemberDAO memberDAO;

    private final QHospitalReview qHospitalReview = QHospitalReview.hospitalReview;

    // 모든 게시글 보기
    public Page<HospitalReview> showAll(Pageable pageable) {
        return hospitalReviewDAO.findAll(pageable);
    }

    public Page<HospitalReview> searchHr (String keyword, int select, Pageable pageable) {
        JPAQuery result = null;
        if(select == 1) {
            result = queryFactory.selectFrom(qHospitalReview)
                    .where(qHospitalReview.hospitalName.contains(keyword))
                    .orderBy(qHospitalReview.hospitalReviewCode.desc());
        } else if (select == 2) {
            result = queryFactory.selectFrom(qHospitalReview)
                    .where(qHospitalReview.hospitalAddress.contains(keyword))
                    .orderBy(qHospitalReview.hospitalReviewCode.desc());
        }

        long totalCount = result.fetchCount();
        result.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<HospitalReview> resultList = result.fetch();

        return new PageImpl<>(resultList, pageable, totalCount);
    }

    // 게시글 한 개 보기
    public HospitalReview show(int hospitalReviewCode) {

        HospitalReview hospitalReview = hospitalReviewDAO.findById(hospitalReviewCode).orElse(null);
        return hospitalReview;
    }

    public int updateViewCount(int hospitalReviewCode) {
        return hospitalReviewDAO.updateViews(hospitalReviewCode);
    }

    // 게시글 작성하기
    public HospitalReview create(HospitalReview hospitalReview) {
        return hospitalReviewDAO.save(hospitalReview);
    }

    // 게시글 수정하기
    public HospitalReview update(HospitalReview hospitalReview) {
        HospitalReview target = hospitalReviewDAO.findById(hospitalReview.getHospitalReviewCode()).orElse(null);
        if (target != null) {
            hospitalReviewDAO.save(hospitalReview);
        }
        return null;
    }

    // 게시글 삭제하기
    public HospitalReview delete(int hospitalReviewCode) {
        HospitalReview hospitalReview = hospitalReviewDAO.findById(hospitalReviewCode).orElse(null);
        hospitalReviewDAO.delete(hospitalReview);
        return hospitalReview;
    }

    // 게시글 좋아요 하기
    public HospitalReview updateLike(int hospitalReviewCode) {
        hospitalReviewDAO.updateLike(hospitalReviewCode);
        return hospitalReviewDAO.findById(hospitalReviewCode).orElse(null);
    }

    // 게시글 좋아요 취소
    public HospitalReview deleteLike(int hospitalReviewCode) {
        hospitalReviewDAO.deleteLike(hospitalReviewCode);
        return hospitalReviewDAO.findById(hospitalReviewCode).orElse(null);
    }
}
