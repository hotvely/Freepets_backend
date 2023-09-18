package com.kh.Freepets.domain.member;

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
public class Bookmark
{
    @Id
    @Column(name = "bookmark_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bookmarkSeq")
    @SequenceGenerator(name = "bookmarkSeq", sequenceName = "SEQ_BOOKMARK", allocationSize = 1)
    private int bookmarkCode;

    @Column(name = "board_code")
    private int boardCode;

    @Column(name = "post_code")
    private int postCode;

    @Column(name = "bookmark_date")
    private Date bookmarkDate;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

}

