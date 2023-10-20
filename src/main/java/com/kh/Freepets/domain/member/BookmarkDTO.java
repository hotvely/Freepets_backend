package com.kh.Freepets.domain.member;

import com.kh.Freepets.BoardType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO
{
    private String boardName;
    private int postCode;
    private String token;
}
