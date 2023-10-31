package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.CommunityLike;
import com.kh.Freepets.repo.board.community.CommunityLikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
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

    public CommunityLike delete(int commonLikeCode){
        CommunityLike target = commonLikeDAO.findById(commonLikeCode).orElse(null);
        commonLikeDAO.delete(target);
        return target;
    }

    public CommunityLike likesBymemberAndCommunity(String id, int commonCode) {
        return commonLikeDAO.commonLikesByMemberAndCommunity(id, commonCode);
    }

}
