package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.CommunityComment;
import com.kh.Freepets.domain.board.community.LostComment;
import com.kh.Freepets.repo.board.community.CommunityCommentDAO;
import com.kh.Freepets.repo.board.community.CommunityDAO;
import com.kh.Freepets.repo.board.community.LostCommentDAO;
import com.kh.Freepets.repo.board.community.LostDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class LostCommentService {
    @Autowired
    private LostCommentDAO lostCommentDAO;
    @Autowired
    private LostDAO lostDAO;

    public List<LostComment> lostCommentAll(int lostCode){
        log.info("왜 안나와아ㅏㅏ");
        return lostCommentDAO.lostCommentAll(lostCode);
    }
    public List<LostComment> lostReCommentAll(int lostCommentCodeSuper){
        return lostCommentDAO.lostReCommentAll(lostCommentCodeSuper);
    }
    public LostComment showLostComment(int lostCommentCode) {
        log.info("왜 안나와아ㅏㅏ");
        return lostCommentDAO.findById(lostCommentCode).orElse(null);
    }

    public LostComment create(LostComment lostComment){
        return lostCommentDAO.save(lostComment);
    }

    public LostComment update(LostComment lostComment){
        LostComment target = lostCommentDAO.findById(lostComment.getLostCommentCode()).orElse(null);
        if(target != null){
            return lostCommentDAO.save(lostComment);
        }
        return null;
    }

    public LostComment delete(int lostCommentCode){
        LostComment target = lostCommentDAO.findById(lostCommentCode).orElse(null);
        lostCommentDAO.delete(target);
        return target;
    }
}
