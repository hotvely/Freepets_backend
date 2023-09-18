package com.kh.Freepets.repo.member;

import com.kh.Freepets.domain.member.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookmarkDAO extends JpaRepository<Bookmark, Integer>
{
    // 특정 유저의 아이디로 모든 북마크 부르기
    @Query(value = "SELECT * FROM bookmark WHERE id=?")
    List<Bookmark> findByMemberId(String id);
}
