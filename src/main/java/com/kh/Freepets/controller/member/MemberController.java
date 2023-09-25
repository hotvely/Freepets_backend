package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class MemberController
{
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/auth/member")      //<- 모든 멤버 조회
    public ResponseEntity<List<Member>> findAllMember()
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll());
    }

    @GetMapping("/auth/member/{id}")
    public ResponseEntity showMypage(@PathVariable String id)
    {   // 마이 페이지.. 할건데.. 잠시 대기 프론트에 전역을 쓸 토큰 넘겨 받는법 알아야함

        return null;
    }

    // sign up 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto)
    {
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .authority(dto.getAuthority())
                .build();

        Member registerMember = memberService.create(member);
        MemberDTO responseDTO = MemberDTO.builder().id(registerMember.getId())
                .name(registerMember.getName())
                .authority(registerMember.getAuthority())
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    // sign in 로그인
    @PostMapping("/auth/signin")
    public ResponseEntity<MemberDTO> authenticate(@RequestBody MemberDTO dto)
    {
        Member member = memberService.getByCredentials(dto.getId(),
                                                       dto.getPassword(),
                                                       passwordEncoder);

        if (member != null)
        {
            String token = tokenProvider.create(member);
            MemberDTO responseDTO = MemberDTO.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .authority(member.getAuthority())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }
        else
            return ResponseEntity.ok().build();

    }


    @PutMapping("/user")
    public ResponseEntity<Member> updateUser(@RequestBody Member member)
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.update(member));
    }

//    @PutMapping("/admin")
//    public ResponseEntity<Member> updateAdmin(@RequestBody Member member)
//    {
//        if (member.get)
//            return ResponseEntity.status(HttpStatus.OK).body(memberService.update(member));
//    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<Member> delete(@PathVariable String id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.delete(id));
    }
}
