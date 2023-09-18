package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.PrLike;
import com.kh.Freepets.repo.board.information.PrLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrLikeService {

    @Autowired
    private PrLikeDAO dao;

    public List<PrLike> showAll() {
        return dao.findAll();
    }

    public PrLike show(int prLikeCode) {
        return dao.findById(prLikeCode).orElse(null);
    }

    public PrLike likeMember(String id, int productReivewCode) {
        return dao.likeMember(id, productReivewCode);
    }

    public PrLike prAddLike(PrLike prLike) {
        return dao.save(prLike);
    }

    public PrLike prUpdateLike(PrLike prLike) {
        PrLike target = dao.findById(prLike.getPrLikeCode()).orElse(null);
        if(target != null) {
            return dao.save(prLike);
        }
        return null;
    }

    public PrLike prDeleteLike(int prLikeCode) {
        PrLike prLike = dao.findById(prLikeCode).orElse(null);
        dao.delete(prLike);
        return prLike;
    }
}
