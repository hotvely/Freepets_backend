package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.Media;
import com.kh.Freepets.repo.board.community.MediaDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MediaService {
    @Autowired
    private MediaDAO mediaDAO;

    public List<Media> mediaAll(){
        return mediaDAO.findAll();
    }
    public Media showMedia(int id) {
        return mediaDAO.findById(id).orElse(null);
    }

    public List<Media> findByMemberId (String id){
        return mediaDAO.findByMemberId(id);
    }

    public Media create(Media mediaLike){
        return mediaDAO.save(mediaLike);
    }

    public Media update(Media mediaLike){
        Media target = mediaDAO.findById(mediaLike.getMediaCode()).orElse(null);
        if(target != null){
            return mediaDAO.save(mediaLike);
        }
        return null;
    }

    public Media delete(int mediaCode){
        Media target = mediaDAO.findById(mediaCode).orElse(null);
        mediaDAO.delete(target);
        return target;
    }
}
