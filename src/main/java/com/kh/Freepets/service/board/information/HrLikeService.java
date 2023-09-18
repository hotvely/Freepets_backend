package com.kh.Freepets.service.board.information;

import com.kh.Freepets.domain.board.information.HrLike;
import com.kh.Freepets.domain.board.information.PrLike;
import com.kh.Freepets.repo.board.information.HrLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrLikeService {

    @Autowired
    private HrLikeDAO dao;

    public List<HrLike> showAll() {
        return dao.findAll();
    }

    public HrLike show(int hrLikeCode) {
        return dao.findById(hrLikeCode).orElse(null);
    }

    public HrLike likeMember(String id, int hospitalReviewCode) {
        return dao.likeMember(id, hospitalReviewCode);
    }

    public HrLike hrAddLike(HrLike hrLike) {
        return dao.save(hrLike);
    }

    public HrLike hrUpdateLike(HrLike hrLike) {
        HrLike target = dao.findById(hrLike.getHrLikeCode()).orElse(null);
        if(target != null) {
            return dao.save(hrLike);
        }
        return null;
    }

    public HrLike hrDeleteLike(int hrLikeCode) {
        HrLike hrLike = dao.findById(hrLikeCode).orElse(null);
        dao.delete(hrLike);
        return hrLike;
    }
}
