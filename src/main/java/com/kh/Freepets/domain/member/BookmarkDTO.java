package com.kh.Freepets.domain.member;

import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.board.BoardDTO;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkDTO
{
    private int bookmarkCode;
    private String boardName;
    private int postCode;
    private String token;
    private String id;
    private String nickname;
    private BoardDTO boardDTO;
    private String url;
}
