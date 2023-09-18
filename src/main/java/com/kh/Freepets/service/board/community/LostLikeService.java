package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.LostLike;
import com.kh.Freepets.repo.board.community.LostLikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LostLikeService {

    @Autowired
    private LostLikeDAO dao;


    public List<LostLike> showAllLostLike() {
        return dao.findAll();
    }


    public LostLike showLostLike(int lostlikeCode){

        LostLike lostlike = dao.findById(lostlikeCode).orElse(null);
        return lostlike;
    }


    public LostLike create(LostLike lostlike){
        return dao.save(lostlike);
    }


    public LostLike update(LostLike lostlike){
        LostLike target =  dao.findById(lostlike.getLostLikeCode()).orElse(null);
        if(target!=null){
            return dao.save(lostlike);
        }
        return null;
    }


    public LostLike delete(int lostlikeCode){
        LostLike target =  dao.findById(lostlikeCode).orElse(null);
        dao.delete(target);
        return target;
    }

    public LostLike noDoubleLike(String id, int lostCode){
        LostLike lostlike = dao.noDoubleLike(id, lostCode);
        return lostlike;
    }
}
