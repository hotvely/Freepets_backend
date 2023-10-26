package com.kh.Freepets.repo.board.notice;

import com.kh.Freepets.domain.board.notice.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event, Integer>
{
}
