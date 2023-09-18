package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class MemberController
{
    @Autowired
    private MemberService memberService;


    @GetMapping("/member")      //<- 모든 멤버 조회
    public ResponseEntity<List<Member>> findAllMember()
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll());
    }
//
//    @GetMapping("/user")    //<- 유저만 조회
//    public ResponseEntity<List<Member>> findAllUser()
//    {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(memberService.findAll_user_admin("USER"));
//    }
//
//    @GetMapping("/admin")   // <- 관리자만 조회
//    public ResponseEntity<List<Member>> findAllAdmins()
//    {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(memberService.findAll_user_admin("ADMIN"));
//    }
//
//    @GetMapping("/member/{id}")     //<- 모든 멤버중 특정 아이디 조회
//    public ResponseEntity<Member> findById(@PathVariable String id)
//    {
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.findById(id));
//    }
//
//    @GetMapping("/user/{id}")     //<- 유저 중 특정 아이디 조회
//    public ResponseEntity<Member> findByIdUser(@PathVariable String id)
//    {
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.findByIdUser(id));
//    }
//
//    @GetMapping("/admin/{id}")     //<- 관리자 중 특정 아이디 조회
//    public ResponseEntity<Member> findByIdAdmin(@PathVariable String id)
//    {
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.findByIdAdmin(id));
//    }

    @PostMapping("/user")
    public ResponseEntity<Member> userCreate(@RequestBody Member member)
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.create(member));
    }
//
//    @PostMapping("/admin")
//    public ResponseEntity<Member> adminCreate(@RequestBody Member member)
//    {
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.createAdmin(member));
//    }

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
