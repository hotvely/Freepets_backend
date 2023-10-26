package com.kh.Freepets.repo.board.notice;

import com.kh.Freepets.domain.board.notice.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventDAO extends JpaRepository<Event, Integer>
{
    @Query(value = "SELECT * FROM EVENT WHERE YEAR = :year", nativeQuery = true)
    List<Event> showYear(int year);


}
