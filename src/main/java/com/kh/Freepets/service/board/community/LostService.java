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

    // 검색
   public Page<Lost> searchKeywordAll(String keyword, Pageable pageable){
        return lostDAO.searchKeywordAll(keyword,pageable);
    }

    public Page<Lost> searchTitle(String keyword, Pageable pageable){
        return lostDAO.searchTitle(keyword,pageable);
    }

    public Page<Lost> searchDesc(String keyword, Pageable pageable){
        return lostDAO.searchDesc(keyword,pageable);
    }


}
