package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.repo.board.community.CommunityCommentDAO;
import com.kh.Freepets.repo.board.community.CommunityDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CommunityCommentService {
    @Autowired
    private CommunityCommentDAO commonCommentDAO;
    @Autowired
    private CommunityDAO commonDAO;

    public List<CommunityComment> commonCommentAll(int commonCode){
        log.info("왜 안나와아ㅏㅏ");
        return commonCommentDAO.commonCommentAll(commonCode);
    }
    public List<CommunityComment> commonReCommentAll(int commonCommentCodeSuper){
        return commonCommentDAO.commonReCommentAll(commonCommentCodeSuper);
    }
    public CommunityComment showCommonComment(int commonCommentCode) {
        log.info("왜 안나와아ㅏㅏ");
        return commonCommentDAO.findById(commonCommentCode).orElse(null);
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

    public CommunityComment delete(int commonCommentCode){
        CommunityComment target = commonCommentDAO.findById(commonCommentCode).orElse(null);
        commonCommentDAO.delete(target);
        return target;
    }
}
