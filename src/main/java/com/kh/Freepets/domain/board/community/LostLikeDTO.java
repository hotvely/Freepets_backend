package com.kh.Freepets.domain.board.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LostLikeDTO
{
    private int postCode;
    private String token;
}