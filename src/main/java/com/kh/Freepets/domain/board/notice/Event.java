package com.kh.Freepets.domain.board.notice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
public class Event
{
    @Id
    @Column(name = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "eventSequence")
    @SequenceGenerator(name = "eventSequence", sequenceName = "SEQ_EVENT", allocationSize = 1)
    private int eventCode;

    @Column(name = "EVENT_TITLE")
    private String eventTitle;

    @Column(name = "EVENT_ADDRESS")
    private String eventAddress;

    @Column(name = "EVENT_URL")
    private String eventURL;

    @Column(name = "EVENT_PRICE")
    private int eventPrice;

    @Column(name = "EVENT_DESC")
    private String eventDesc;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "MONTH")
    private int month;
    
    @Column(name = "DAY")
    private int day;
}
