package com.kh.Freepets.domain.board.notice;

import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class NoticeLike
{

    @Id
    @Column(name = "NOTICE_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "noticelikeSequence")
    @SequenceGenerator(name = "noticelikeSequence", sequenceName = "SEQ_LIKE_NOTICE", allocationSize = 1)
    private int noticeLikeCode;


    @Column(name = "NOTICE_LIKE_DATE")
    private Date noticeLikeDate;

    @ManyToOne
    @JoinColumn(name = "NOTICE_CODE")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;


}
