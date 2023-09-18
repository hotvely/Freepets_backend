package com.kh.Freepets.domain.util;

import lombok.Data;

@Data
public class Paging
{
    private int startPage;
    private int endPage;
    private boolean prev = false;
    private boolean next = false;
    private int num = 5;        //최대로 누를수 있는 페이지의 개수

    private int total;        //전체 개수!
    private Criteria cri;

    public Paging(Criteria cri, int total)
    {
        this.cri = cri;
        this.total = total;


        System.out.println("curr page : " + cri.getPage());
        this.endPage = (int) Math.ceil((double) cri.getPage() / this.num) * this.num;
        this.startPage = this.endPage - this.num + 1;
        System.out.println("start : " + startPage + " end : " + endPage);
        int lastPage = (int) (total / cri.getAmount()) + 1;
        System.out.println("lasetpage :  " + lastPage);
        if (lastPage < this.endPage)
        {
            this.endPage = lastPage;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < lastPage;

        System.out.println("prev   /" + prev);
        System.out.println("next   /" + next);
    }


}
