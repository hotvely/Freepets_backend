package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.domain.board.community.LostLike;
import com.kh.Freepets.repo.board.community.CommunityLikeDAO;
import com.kh.Freepets.repo.board.community.LostLikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class LostLikeService {
    @Autowired
    private LostLikeDAO lostLikeDAO;

    public List<LostLike> lostLikeAll(){
        return lostLikeDAO.findAll();
    }
    public LostLike showlostLike(int id) {
        return lostLikeDAO.findById(id).orElse(null);
    }

    public LostLike create(LostLike lostLike){
        return lostLikeDAO.save(lostLike);
    }

    public LostLike update(LostLike lostLike){
        LostLike target = lostLikeDAO.findById(lostLike.getLostLikeCode()).orElse(null);
        if(target != null){
            return lostLikeDAO.save(lostLike);
        }
        return null;
    }

    public LostLike delete(int lostLikeCode){
        LostLike target = lostLikeDAO.findById(lostLikeCode).orElse(null);
        lostLikeDAO.delete(target);
        return target;
    }

    public LostLike likesBymemberAndCommunity(String id, int lostLikeCode) {
        return lostLikeDAO.lostLikesByMemberAndLost(id, lostLikeCode);
    }

}
