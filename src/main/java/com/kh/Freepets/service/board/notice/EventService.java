package com.kh.Freepets.service.board.notice;

import com.kh.Freepets.domain.board.notice.Event;
import com.kh.Freepets.repo.board.notice.EventDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventService
{
    @Autowired
    private EventDAO dao;

    public List<Event> showAll()
    {
        return dao.findAll();
    }

    public List<Event> showYear(int year)
    {
        return dao.showYear(year);

    }


    public Event create(Event event)
    {
        return dao.save(event);
    }


}
