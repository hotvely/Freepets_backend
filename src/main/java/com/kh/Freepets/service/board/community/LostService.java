package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.Lost;
import com.kh.Freepets.domain.board.community.LostLike;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.community.LostDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LostService {
    @Autowired
    private LostDAO dao;

    @Autowired
    private MemberDAO memberdao;

    public List<Lost> showAll(){
        return dao.findAll();
    }

    public Lost show(int lostCode){
        Lost lost =  dao.findById(lostCode).orElse(null);
       // lost.setLostViews(lost.getLostViews()+1);  // 조회수 증가 메소드
        return lost;
    }


    public Lost create(Lost lost){
          Member member =   memberdao.findById(lost.getMember().getId()).orElse(null);
          lost.setMember(member);
        return dao.save(lost);
    }

    public Lost update(Lost lost){
             Lost target = dao.findById(lost.getLostCode()).orElse(null);
             if(target!= null){
                 return dao.save(lost);
             }
             return null;
    }

    public Lost delete(int lostCode){
        Lost target = dao.findById(lostCode).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<Lost> showMember(String id){ //특정 유저의 분실 게시물 조회
       Member member =  memberdao.findById(id).orElse(null);
       List<Lost> target = dao.findByMemberId(member.getId());
       return target;
    }

    // 게시글 좋아요 갯수 업데이트
    public Lost updatelike(int lostCode){
        dao.updateLostlike(lostCode);
        return dao.findById(lostCode).orElse(null);

    }

    // 게시글 좋아요 갯수 차감
    public Lost deletelike(int lostCode){
        dao.deleteLostlike(lostCode);
        return dao.findById(lostCode).orElse(null);
    }

    // 게시글 댓글 갯수 업데이트
    public Lost updateLostCommentCount(int lostCode){
        dao.updateLostCommentCount(lostCode);
        return dao.findById(lostCode).orElse(null);
    }


    // 게시글 댓글 갯수 차감
    public Lost deleteLostCommentCount(int lostCode){
        dao.deleteLostCommentCount(lostCode);
        return dao.findById(lostCode).orElse(null);
    }

    // 게시글 댓글 갯수 정렬
    public List<Lost> sortCommentCount(){
        return dao.sortCommentCount();
    }



    // 게시글 좋아요 수 정렬
    public List<Lost> sortLostLike(){
        return dao.sortLostLike();
    }


}
