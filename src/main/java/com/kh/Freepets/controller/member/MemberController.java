package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.file.FileInputHandler;
import com.kh.Freepets.service.member.EmailService;
import com.kh.Freepets.service.member.MemberService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class MemberController
{
    static final int tempPwd_size = 8;       //만드려고 하는 임시 비밀번호의 사이즈
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String tempPwd = RandomStringUtils.randomAlphanumeric(tempPwd_size);
    @Autowired
    private MemberService memberService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private FileInputHandler fileInputHandler;

    @GetMapping("/showAll")      //<- 모든 멤버 조회
    public ResponseEntity<List<Member>> findAllMember()
    {

        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll());
    }

    @GetMapping("/findMember/{id}")
    public ResponseEntity<MemberDTO> findMemberById(@PathVariable String id)
    {
        Member member = memberService.findByIdUser(id);

        if (member != null)
        {
            MemberDTO responseDTO = MemberDTO.builder()
                    .memberImg(member.getMemberImg())
                    .memberInfo(member.getMemberInfo())
                    .email(member.getEmail())
                    .birth(member.getBirth())
                    .gender(member.getGender())
                    .nickname(member.getNickname())
                    .name(member.getName())
                    .id(member.getId())
                    .createAccountDate(member.getCreateAccountDate())
                    .build();

            return ResponseEntity.ok().body(responseDTO);
        }
        return ResponseEntity.badRequest().build();

    }


    @PostMapping("/findId")     //아이디 찾기
    public ResponseEntity<String> findId(@RequestBody MemberDTO memberDTO)
    {

        log.info("memberController 아이디 찾기 기능");
        log.info(memberDTO.toString());
        String userid = memberService.findId(memberDTO);
        log.info(userid);

        return ResponseEntity.ok().body(userid);
    }

    @PostMapping("/findPwd")        //비밀번호 찾기
    public ResponseEntity<String> findPwd(@RequestBody MemberDTO memberDTO)
    {
        String userPwd = memberService.findPwd(memberDTO);
        log.info("DB저장되어 있는 PWD : " + userPwd);
        log.info("랜덤하게 생성한 비밀번호 : " + tempPwd);
        try
        {
            String result = emailService.sendEmail(memberDTO.getEmail(), tempPwd);
            // 이메일 보내기가 성공하게 되면 DB에 정보 바꿔야 사용자가 변경된 비밀번호로 접근이 가능함
            if (result.equals("Success"))
            {
                // DB에서 맴버 객체 들고와서
                Member member = memberService.findByIdUser(memberDTO.getId());
                // 랜덤 생성한 비밀번호를 DB에 넣을 때에는 무조건 암호화 해서 !
                member.setPassword(passwordEncoder.encode(tempPwd));

                Member updateMember = memberService.update(member);

                if (updateMember != null)
                    return ResponseEntity.ok().body(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }

    }


    @PostMapping("/register") // sign up 회원가입
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto)
    {
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
                .memberImg("/upload/yaonge.jpg")
                .createAccountDate(new Date())
                .deleteAccountYN('N')
                .authority("USER")          //우선 기본적인거.. user로 넣기..
                .build();


        Member registerMember = memberService.create(member);

        if (registerMember != null)
        {
            MemberDTO responseDTO = MemberDTO.builder().id(registerMember.getId())
                    .name(registerMember.getName())
                    .authority(registerMember.getAuthority())
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

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

        if (member.getAuthority().equals("ADMIN"))
        {
            member.setNickname("관리자");
        }

        log.info("로그인...... : " + member);
        if (member != null)
        {
            String token = tokenProvider.create(member);
            MemberDTO responseDTO = memberService.createDTO(member, token);

            return ResponseEntity.ok().body(responseDTO);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    @PutMapping("/update")  // 마이페이지에서 멤버 업데이트(수정)
    public ResponseEntity<MemberDTO> updateUser(@RequestParam(name = "token") String token,
                                                @RequestParam(name = "password") String password,
                                                @RequestParam(name = "nickname") String nickname,
                                                @RequestParam(name = "email") String email,
                                                @RequestParam(name = "phone") String phone,
                                                @RequestParam(name = "memberInfo") String memberInfo,
                                                @RequestParam(name = "file") MultipartFile file)
    {


        // 전달 받은 데이터로 변경 할 멤버 객체 만듬
        String userId = tokenProvider.validateAndGetUserId(token);
        log.info("받은 토큰을 이용해 유저 아이디 찾기 : " + userId);

        // db에 있는 member 객체...
        Member member = memberService.findByIdUser(userId);

        log.info("member : " + member.toString());

        if (!password.isEmpty())
        {
            member.setPassword(passwordEncoder.encode(password));
        }

        if (!nickname.isEmpty())
        {
            member.setNickname(nickname);
        }

        if (!email.isEmpty())
        {
            member.setEmail(email);
        }

        if (!phone.isEmpty())
        {
            member.setPhone(phone);
        }


        if (!memberInfo.isEmpty())
        {
            member.setMemberInfo(memberInfo);
        }

        if (file != null) ;
        {
            String filepath = "/upload/" + fileInputHandler.fileInput(file);

            member.setMemberImg(filepath);
        }


        log.info("데이터 집어넣어서 DB로 보내기 전에 멤버 객체 : " + member);
        // 받은 데이터를 통해서 DB에 업데이트 한 이후 객체..
        Member updateMember = memberService.update(member);
        log.info("updateMember : " + updateMember);

        // 다시 프론트로 던질 중요 정보 제외한 객체 생성
        MemberDTO responseDTO = memberService.createDTO(updateMember, token);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/delete")
    public ResponseEntity<MemberDTO> delete(@RequestBody MemberDTO memberDTO)
    {
        log.info("삭제 들어옴?");
        log.info(memberDTO.toString());
        String token = memberDTO.getToken();
        // 전달 받은 데이터로 변경 할 멤버 객체 만듬
        String userId = tokenProvider.validateAndGetUserId(token);
        log.info("받은 토큰을 이용해 유저 아이디 찾기 : " + userId);

        // db에 있는 member 객체...
        Member member = memberService.findByIdUser(userId);
        member.setDeleteAccountYN('Y');

        Member updateMember = memberService.update(member);
        MemberDTO responseDTO = memberService.createDTO(updateMember, token);

        return ResponseEntity.ok().body(responseDTO);
    }
}
