package com.kh.Freepets.service.member;

import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.repo.member.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberService
{
    @Autowired
    private MemberDAO dao;

    public List<Member> findAll()
    {
        //SELECT * FROM MEMBER
        return dao.findAll();
    }

//    public List<Member> findAll_user_admin(String condition)
//    {
//        if (condition.equals("ADMIN") || condition.equals("USER"))
//        {
//            return dao.findByAuthority(condition);
//        }
//
//        return null;
//    }
//
//    public Member findById(String id)
//    {
//        //SELECT * FROM MEMBER WHERE ID=?
//        return dao.findById(id).orElse(null);
//    }
//
//    public Member findByIdAdmin(String id)
//    {
//        Member member = dao.findById(id).orElse(null);
//        if (member != null && member.getAuthority().equals("ADMIN"))
//            return member;
//
//        log.info("없는 계정이거나 관리자 계정이 아님");
//        return null;
//    }
//
//    public Member findByIdUser(String id)
//    {
//        Member member = dao.findById(id).orElse(null);
//        if (member != null && member.getAuthority().equals("USER"))
//            return member;
//
//        log.info("없는 계정이거나 유저 계정이 아님");
//        return null;
//    }

    public Member create(Member member)
    {
        Member target = dao.findById(member.getId()).orElse(null);

        if (target != null)
        {   // 존재함 -> 이미 생성된 계정
            log.info("member create failed (account existed) : " + target);
            return null;
        }
        else
        {   // 존재하지않음 -> 생성가능한 계정
            log.info("member create success : " + member);
            return dao.save(member);
        }

    }

//    public Member createAdmin(Member member)
//    {
//        Member target = dao.findById(member.getId()).orElse(null);
//
//        if (target != null)
//            return null;
//        else
//        {
//            member.setAuthority("ADMIN");
//            return dao.save(member);
//        }
//    }

    public Member update(Member member)
    {
        Member target = dao.findById(member.getId()).orElse(null);
        if (target != null)
        {   // 존재함 -> 업데이트 가능
            log.info("member update success : " + member);
            return dao.save(member);
        }
        else
        {   // 계정이 존재하지 않음 -> 업데이트 불가능
            log.info("member update failed (account not existed) : " + target);
            return dao.save(target);
        }
    }

    public Member delete(String id)
    {
        Member target = dao.findById(id).orElse(null);
        if (target != null)
            dao.delete(target);

        return target;
    }

}
