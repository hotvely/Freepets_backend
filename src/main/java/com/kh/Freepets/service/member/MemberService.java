package com.kh.Freepets.service.member;

import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.repo.member.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private MemberDAO dao;

    public String findId(MemberDTO memberDTO) {
        return dao.findId(memberDTO.getName(), memberDTO.getEmail());

    }

    public String findPwd(MemberDTO memberDTO) {
        return dao.findPwd(memberDTO.getId(), memberDTO.getEmail());
    }


    public List<Member> findAll() {
        //SELECT * FROM MEMBER
        return dao.findAll();
    }


    public Member findByIdUser(String id) {
        Member member = dao.findById(id).orElse(null);
        if (member != null)
            return member;

        log.info("없는 계정이거나 유저 계정이 아님");
        return null;
    }

    public Member create(Member member) {
        Member target = dao.findById(member.getId()).orElse(null);

        if (target != null) {   // 존재함 -> 이미 생성된 계정
            log.info("member create failed (account existed) : " + target);
            return null;
        } else {   // 존재하지않음 -> 생성가능한 계정
            log.info("member create success : " + member);
            return dao.save(member);
        }

    }

    public Member update(Member member) {
        Member target = dao.findById(member.getId()).orElse(null);
        if (target != null) {   // 존재함 -> 업데이트 가능
            log.info("member update success : " + member);
            return dao.save(member);
        } else {   // 계정이 존재하지 않음 -> 업데이트 불가능
            log.info("member update failed (account not existed) : " + target);
            return dao.save(target);
        }
    }

    public Member delete(String id) {
        Member target = dao.findById(id).orElse(null);
        if (target != null && target.getDeleteAccountYN() == 'N') {
            target.setDeleteAccountYN('Y');
            dao.save(target);
            dao.delete(target);
        }

        return target;
    }

    public Member getByCredentials(String id, String password, PasswordEncoder passwordEncoder) {
        Member target = dao.findById(id).orElse(null);
        if (target != null && passwordEncoder.matches(password, target.getPassword())) {
            return target;
        }
        return null;
    }

    public MemberDTO createDTO(Member member, String token) {
        MemberDTO responseDTO = MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .authority(member.getAuthority())
                .email(member.getEmail())
                .phone(member.getPhone())
                .birth(member.getBirth())
                .memberImg(member.getMemberImg())
                .memberInfo(member.getMemberInfo())
                .gender(member.getGender())
                .address(member.getAddress())
                .createAccountDate(member.getCreateAccountDate())
                .deleteAccountYN(member.getDeleteAccountYN())

                .token(token)
                .build();

        return responseDTO;
    }

}
