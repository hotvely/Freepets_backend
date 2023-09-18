package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.PrLike;
import com.kh.Freepets.domain.board.information.ViLike;
import com.kh.Freepets.repo.board.information.ViLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViLikeService {

    @Autowired
    private ViLikeDAO dao;

    public List<ViLike> showAll() {
        return dao.findAll();
    }

    public ViLike show(int viLikeCode) {
        return dao.findById(viLikeCode).orElse(null);
    }

    public ViLike likeMember(String id, int videoInfoCode) {
        return dao.likeMember(id, videoInfoCode);
    }

    public ViLike viAddLike(ViLike viLike) {
        return dao.save(viLike);
    }

    public ViLike viUpdateLike(ViLike viLike) {
        ViLike target = dao.findById(viLike.getViLikeCode()).orElse(null);
        if(target != null) {
            return dao.save(viLike);
        }
        return null;
    }

    public ViLike viDeleteLike(int viLikeCode) {
        ViLike viLike = dao.findById(viLikeCode).orElse(null);
        dao.delete(viLike);
        return viLike;
    }
}
