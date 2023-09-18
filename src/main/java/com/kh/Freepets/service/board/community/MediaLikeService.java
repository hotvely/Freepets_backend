package com.kh.Freepets.service.board.community;

import com.kh.Freepets.domain.board.community.MediaLike;
import com.kh.Freepets.repo.board.community.MediaLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaLikeService {
    @Autowired
    private MediaLikeDAO mediaLikeDAO;

    public List<MediaLike> mediaLikeAll(){
        return mediaLikeDAO.findAll();
    }
    public MediaLike showMediaLike(int id) {
        return mediaLikeDAO.findById(id).orElse(null);
    }

    public MediaLike create(MediaLike mediaLike){
        return mediaLikeDAO.save(mediaLike);
    }

    public MediaLike update(MediaLike mediaLike){
        MediaLike target = mediaLikeDAO.findById(mediaLike.getMediaLikeCode()).orElse(null);
        if(target != null){
            return mediaLikeDAO.save(mediaLike);
        }
        return null;
    }

    public MediaLike delete(int id){
        MediaLike target = mediaLikeDAO.findById(id).orElse(null);
        mediaLikeDAO.delete(target);
        return target;
    }
}
