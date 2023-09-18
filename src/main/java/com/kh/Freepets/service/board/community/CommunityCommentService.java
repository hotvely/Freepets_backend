package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.repo.board.community.CommunityCommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityCommentService {
    @Autowired
    private CommunityCommentDAO commonCommentDAO;

    public List<CommunityComment> commonCommentAll(){
        return commonCommentDAO.findAll();
    }
    public CommunityComment showCommonComment(int id) {
        return commonCommentDAO.findById(id).orElse(null);
    }

    public List<CommunityComment> findByCommonCode (int code){
        return commonCommentDAO.findByCommonCode(code);
    }

    public CommunityComment create(CommunityComment commonComment){
        return commonCommentDAO.save(commonComment);
    }

    public CommunityComment update(CommunityComment commonComment){
        CommunityComment target = commonCommentDAO.findById(commonComment.getCommonCommentCode()).orElse(null);
        if(target != null){
            return commonCommentDAO.save(commonComment);
        }
        return null;
    }

    public CommunityComment delete(int id){
        CommunityComment target = commonCommentDAO.findById(id).orElse(null);
        commonCommentDAO.delete(target);
        return target;
    }
}
