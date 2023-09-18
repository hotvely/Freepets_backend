package com.kh.Freepets.repo.member;

import com.kh.Freepets.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberDAO extends JpaRepository<Member, String>
{
    @Query(value = "SELECT * FROM member WHERE authority=:condition", nativeQuery = true)
    List<Member> findByAuthority(String condition);

}
