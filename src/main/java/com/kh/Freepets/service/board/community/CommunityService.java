package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.repo.board.community.CommunityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityDAO commonDAO;

    public List<Community> commonAll(){
        return commonDAO.findAll();
    }
    public Community showCommon(int id) {
        return commonDAO.findById(id).orElse(null);
    }

    public List<Community> findByMemberId (String id){
        return commonDAO.findByMemberId(id);
    }

    public Community create(Community common){
        return commonDAO.save(common);
    }

    public Community update(Community common){
        Community target = commonDAO.findById(common.getCommonCode()).orElse(null);
        if(target != null){
            return commonDAO.save(common);
        }
        return null;
    }

    public Community delete(int id){
        Community target = commonDAO.findById(id).orElse(null);
        commonDAO.delete(target);
        return target;
    }
}
