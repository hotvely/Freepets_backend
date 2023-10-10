package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.member.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class MemberController
{
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/showAll")      //<- 모든 멤버 조회
    public ResponseEntity<List<Member>> findAllMember()
    {

        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll());
    }

    @GetMapping("/showMember")
    public ResponseEntity<MemberDTO> showMember(@RequestHeader(name = "Authorization") String headerToken)
    {   // 마이 페이지.. 할건데.. 잠시 대기 프론트에 전역을 쓸 토큰 넘겨 받는법 알아야함
        log.info("일단... showMember 관련 컨트롤러 드러옴");
        try
        {
            log.info(headerToken);
            String token = headerToken.replace("Bearer ", "");
            log.info(token);
            Claims claims = tokenProvider.validateAndGetClaims(token);

            log.info("claims.getId() : " + claims.getSubject());

            MemberDTO memberDTO = MemberDTO.builder()
                    .token(token)
                    .id(claims.getSubject())
                    .name((String) claims.get("name"))
                    .nickname((String) claims.get("nickName"))
                    .email((String) claims.get("email"))
                    .phone((String) claims.get("phone"))
                    .address((String) claims.get("address"))
                    .createAccountDate((Date) claims.get("createAccountDate"))
                    .birth((Date) claims.get("birth"))
                    .authority((String) claims.get("Authority"))
                    .build();

            log.info("?? 왜 실패함?");
            log.info("멤버관련 정보.. 로그인했을때 마이페이지에서.. : " + memberDTO);
            return ResponseEntity.ok().body(memberDTO);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }


    }


    // sign up 회원가입

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto)
    {
        log.info("dto : " + dto);

        //기본 값 넣고..
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .birth(dto.getBirth())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .createAccountDate(new Date())
                .deleteAccountYN('N')
                .authority("USER")          //우선 기본적인거.. user로 넣기..
                .build();


        Member registerMember = memberService.create(member);
        MemberDTO responseDTO = MemberDTO.builder().id(registerMember.getId())
                .name(registerMember.getName())
                .authority(registerMember.getAuthority())
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    // sign in 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberDTO> authenticate(@RequestBody MemberDTO dto)
    {
        log.info("일단 멤버 컨트롤러 로그인 들어옴");
        log.info(dto.getId());
        log.info(dto.getPassword());
        Member member = memberService.getByCredentials(dto.getId(),
                                                       dto.getPassword(),
                                                       passwordEncoder);
        log.info("로그인...... : " + member);
        if (member != null)
        {
            String token = tokenProvider.create(member);
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
