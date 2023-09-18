package com.kh.Freepets.domain.board.freeMarket;

import com.kh.Freepets.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "FREEMARKET")
public class FreeMarket {

    @Id
    @Column(name = "FREEMARKET_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "freeMarketSequence")
    @SequenceGenerator(name = "freeMarketSequence", sequenceName = "SEQ_FREEMARKET" ,allocationSize = 1)
    private int freeMarketCode;

    @Column(name = "FREEMARKET_TITLE")
    private String freeMarketTitle;

    @Column(name = "FREEMARKET_LOC")
    private String freeMarketLoc;

    @Column(name = "FREEMARKET_PRICE")
    private int freeMarketPrice;

    @Column(name = "FREEMARKET_DESC")
    private String freeMarketDesc;

    @Column(name = "FREEMARKET_IMG")
    private String freeMarketImg;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;
}
