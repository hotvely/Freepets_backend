package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.LostComment;
import com.kh.Freepets.repo.board.community.LostCommentDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LostCommentService {

    @Autowired
    private LostCommentDAO dao;


    public List<LostComment> showAll(){
        return dao.findAll();
    }

    public LostComment showLostComment(int lCommentCode){
        LostComment lostcomment =  dao.findById(lCommentCode).orElse(null);
        return lostcomment;
    }

    public LostComment create(LostComment lostcomment){


        return dao.save(lostcomment);
    }

    public LostComment update(LostComment lostcomment){
       LostComment target =  dao.findById(lostcomment.getLCommentCode()).orElse(null);
       if(target!=null){
           return dao.save(target);
       }
       return null;
    }

    public LostComment delete(int lCommentCode ){
        LostComment target = dao.findById(lCommentCode).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<LostComment> findByLostCode(int code){
       return dao.findByLostCode(code);
    }


}

