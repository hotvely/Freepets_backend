package com.kh.Freepets.domain.util;

import com.kh.Freepets.domain.board.BoardDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Paging
{
    private int totalCount; // 전체 글 수
    private int totalPages; // 총 페이지 개수
    private int getNumber; // 현재 페이지 번호
    private boolean hasNext; // 다음 페이지 존재 여부
    private boolean hasPrev; // 이전 페이지 존재 여부
    private boolean isFirst; // 시작 페이지 여부

    private List<BoardDTO> dtoList;
}
