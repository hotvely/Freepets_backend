package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.MediaComment;
import com.kh.Freepets.repo.board.community.MediaCommentDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MediaCommentService {
    @Autowired
    private MediaCommentDAO mediaCommentDAO;

    public List<MediaComment> mediaCommentAll(){
        return mediaCommentDAO.findAll();
    }
    public MediaComment showMediaComment(int id) {
        return mediaCommentDAO.findById(id).orElse(null);
    }

    public List<MediaComment> findByMediaCode (int code){
        return mediaCommentDAO.findByMediaCode(code);
    }

    public MediaComment create(MediaComment mediaComment){
        return mediaCommentDAO.save(mediaComment);
    }

    public MediaComment update(MediaComment mediaComment){
        MediaComment target = mediaCommentDAO.findById(mediaComment.getMediaCommentCode()).orElse(null);
        if(target != null){
            return mediaCommentDAO.save(mediaComment);
        }
        return null;
    }

    public MediaComment delete(int mediaCommentCode){
        MediaComment target = mediaCommentDAO.findById(mediaCommentCode).orElse(null);
        mediaCommentDAO.delete(target);
        return target;
    }
}
