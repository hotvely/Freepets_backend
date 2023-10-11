package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.repo.board.community.CommunityDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CommunityService {
    @Autowired
    private CommunityDAO commonDAO;

    public Page<Community> commonAll(Pageable pageable){
        return commonDAO.findAll(pageable);
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

    public Community delete(int commonCode){
        Community target = commonDAO.findById(commonCode).orElse(null);
        commonDAO.delete(target);
        return target;
    }

    // 게시글 좋아요 총 개수 증가
    public Community increaseCommonLikes(int commonCode){
        Community target = commonDAO.findById(commonCode).orElse(null);
        if(target != null){
            return commonDAO.increaseCommonLikes(commonCode);
        }
        return null;
    }

    public Community decreaseCommonLikes(int commonCode){
        Community target = commonDAO.findById(commonCode).orElse(null);
        if(target != null){
            return commonDAO.decreaseCommonLikes(commonCode);
        }
        return null;
    }

    public Community increaseCommonComments(int commonCode){
        Community target = commonDAO.findById(commonCode).orElse(null);
        if(target != null){
            return commonDAO.increaseCommonComments(commonCode);
        }
        return null;
    }

    public Community decreaseCommonComments(int commonCode){
        Community target = commonDAO.findById(commonCode).orElse(null);
        if(target != null){
            return commonDAO.decreaseCommonComments(commonCode);
        }
        return null;
    }

    // 게시글 정렬
    public List<Community> sortCommonViews(){
        return commonDAO.sortCommonViews();
    }

    public List<Community> sortCommonLikes(){
        return commonDAO.sortCommonLikes();
    }

    public List<Community> sortCommonComments(){
        return commonDAO.sortCommonComments();
    }
}
