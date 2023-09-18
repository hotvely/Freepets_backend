package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.information.ProductReview;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.information.ProductReviewDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewDAO productReviewDAO;

    @Autowired
    private MemberDAO memberDAO;

    // 모든 게시글 보기
    public List<ProductReview> showAll() {
        return productReviewDAO.findAll();
    }

    // 게시글 한 개 보기
    public ProductReview show(int productReviewCode) {

        // 게시글 들어갈 때마다 조회수 올라가기
        productReviewDAO.updateViews(productReviewCode);

        ProductReview productReview = productReviewDAO.findById(productReviewCode).orElse(null);
        Member member = memberDAO.findById(productReview.getMember().getId()).orElse(null);
        productReview.setMember(member);
        return productReview;
    }

    // 게시글 작성하기
    public ProductReview create(ProductReview productReview) {
        return productReviewDAO.save(productReview);
    }

    // 게시글 수정하기
    public ProductReview update(ProductReview productReview) {
        ProductReview target = productReviewDAO.findById(productReview.getProductReviewCode()).orElse(null);
        if (target != null) {
            productReviewDAO.save(productReview);
        }
        return null;
    }

    // 게시글 삭제하기
    public ProductReview delete(int productReviewCode) {
        ProductReview productReview = productReviewDAO.findById(productReviewCode).orElse(null);
        productReviewDAO.delete(productReview);
        return productReview;
    }

    // 게시글 좋아요 하기
    public ProductReview updateLike(int productReviewCode) {
        productReviewDAO.updateLike(productReviewCode);
        return productReviewDAO.findById(productReviewCode).orElse(null);
    }

    // 게시글 좋아요 취소
    public ProductReview deleteLike(int productReviewCode) {
        productReviewDAO.deleteLike(productReviewCode);
        return productReviewDAO.findById(productReviewCode).orElse(null);
    }

    // 좋아요 수 정렬 게시글 보기
    public List<ProductReview> showLike() {
        return productReviewDAO.showLike();
    }

    // 댓글 수 정렬 게시글 보기
    public List<ProductReview> showComment(){
        return productReviewDAO.showComment();
    }
}
