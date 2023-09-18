package com.kh.Freepets.service.board.freeMarket;

import com.kh.Freepets.domain.board.freeMarket.FreeMarket;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.freeMarket.FreeMarketDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeMarketService {

    @Autowired
    private FreeMarketDAO freeMarketDAO;

    @Autowired
    private MemberDAO memberDAO;

    public List<FreeMarket> showall() {
        return freeMarketDAO.findAll();
    }

    public FreeMarket show(int id) {
        FreeMarket freeMarket = freeMarketDAO.findById(id).orElse(null);
        Member member = memberDAO.findById(freeMarket.getMember().getId()).orElse(null);
        freeMarket.setMember(member);
        return freeMarket;
    }
    public FreeMarket create(FreeMarket freeMarket) {
        return freeMarketDAO.save(freeMarket);
    }

    public FreeMarket update(FreeMarket freeMarket) {
        FreeMarket target = freeMarketDAO.findById(freeMarket.getFreeMarketCode()).orElse(null);
        if(target!=null) {
            return freeMarketDAO.save(freeMarket);
        }
        return null;
    }

    public FreeMarket delete(int id) {
        FreeMarket freeMarket = show(id);
        freeMarketDAO.delete(freeMarket);
        return freeMarket;
    }

}
