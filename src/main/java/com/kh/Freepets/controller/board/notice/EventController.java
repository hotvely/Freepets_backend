package com.kh.Freepets.controller.board.notice;

import com.kh.Freepets.domain.board.notice.Event;
import com.kh.Freepets.service.board.notice.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/*")
public class EventController
{

    @Autowired
    EventService eventService;

    @GetMapping("/event")
    public ResponseEntity<List<Event>> showAll(@RequestParam(name = "year") int year)
    {
        log.info("일단 전체 조회 들어는 옴..");
        return ResponseEntity.ok().body(eventService.showYear(year));
    }


    @PostMapping("/event")
    public ResponseEntity<Event> create(@RequestBody Event event)
    {
        log.info(event.toString());
        return ResponseEntity.ok().body(eventService.create(event));
    }


}
