package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.HospitalReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalReviewService {

    @Autowired
    private HospitalReviewDAO hospitalReviewDAO;
    @Autowired
    private MemberDAO memberDAO;

    // 모든 게시글 보기
    public List<HospitalReview> showAll() {
        return hospitalReviewDAO.findAll();
    }

    // 게시글 한 개 보기
    public HospitalReview show(int hospitalReviewCode) {

        // 게시글 들어갈 때마다 조회수 올라가기
        hospitalReviewDAO.updateViews(hospitalReviewCode);

        HospitalReview hospitalReview = hospitalReviewDAO.findById(hospitalReviewCode).orElse(null);
        Member member = memberDAO.findById(hospitalReview.getMember().getId()).orElse(null);
        hospitalReview.setMember(member);
        return hospitalReview;
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

    // 좋아요 수 정렬 게시글 보기
    public List<HospitalReview> showLike() {
        return hospitalReviewDAO.showLike();
    }

    // 댓글 수 정렬 게시글 보기
    public List<HospitalReview> showComment(){
        return hospitalReviewDAO.showComment();
    }

}
