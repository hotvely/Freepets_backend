package com.kh.Freepets.service.board.sitter;

import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.board.sitter.SitterDAO;
import com.kh.Freepets.repo.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitterService {

    @Autowired
    private SitterDAO sitterDAO;

    @Autowired
    private MemberDAO memberDAO;

    public List<Sitter> showall() {
        return sitterDAO.findAll();
    }

    public Sitter show(int id) {
        Sitter sitter = sitterDAO.findById(id).orElse(null);
        Member member = memberDAO.findById(sitter.getMember().getId()).orElse(null);
        sitter.setMember(member);
        return sitter;
    }
    public Sitter create(Sitter sitter) {
        return sitterDAO.save(sitter);
    }

    public Sitter update(Sitter sitter) {
        Sitter target = sitterDAO.findById(sitter.getSitterCode()).orElse(null);
        if(target!=null) {
            return sitterDAO.save(sitter);
        }
        return null;
    }

    public Sitter delete(int id) {
        Sitter freeMarket = show(id);
        sitterDAO.delete(freeMarket);
        return freeMarket;
    }

}
