package com.kh.Freepets.domain.util;


import lombok.Data;
import lombok.Getter;

// 게시판 넘버링 !
// @Getter Annotation만 단 이유는 함부로 Setter를 통해서 접근 할 수 없도록 한 것.
@Getter
public class Criteria
{
    private int page; // 페이지 번호
    private int amount; // 한 페이지 당 몇개의 데이터 보여줄 지.

    public Criteria()
    {
        this(1, 20);
    }

    public Criteria(int page, int amount)
    {
        this.page = page;
        this.amount = amount;
    }
}
