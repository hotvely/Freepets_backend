package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.repo.board.community.CommunityLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommunityLikeService {
    @Autowired
    private CommunityLikeDAO commonLikeDAO;

    public List<CommunityLike> commonLikeAll(){
        return commonLikeDAO.findAll();
    }
    public CommunityLike showCommonLike(int id) {
        return commonLikeDAO.findById(id).orElse(null);
    }

    public CommunityLike create(CommunityLike commonLike){
        return commonLikeDAO.save(commonLike);
    }

    public CommunityLike update(CommunityLike commonLike){
        CommunityLike target = commonLikeDAO.findById(commonLike.getCommonLikeCode()).orElse(null);
        if(target != null){
            return commonLikeDAO.save(commonLike);
        }
        return null;
    }

    public CommunityLike delete(int id){
        CommunityLike target = commonLikeDAO.findById(id).orElse(null);
        commonLikeDAO.delete(target);
        return target;
    }
}
