package com.kh.Freepets.domain.board.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeLikeDTO
{
    private int postCode;
    private String token;
}
