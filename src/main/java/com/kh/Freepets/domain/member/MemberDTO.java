package com.kh.Freepets.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO
{
    // 클라로 넘길 토큰을 가지고 있는 멤버 껍데기 객체
    private String token;
    private String id;
    private String password;
    private String name;
    private LocalDate birth;
    private char gender;
    private String phone;
    private String address;
    private String nickname;
    private String email;
    private LocalDate createAccountDate;
    private char deleteAccountYN;
    private String memberImg;
    private String memberInfo;
    private String authority;

}
