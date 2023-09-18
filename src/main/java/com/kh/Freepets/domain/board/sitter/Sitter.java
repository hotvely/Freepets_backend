package com.kh.Freepets.domain.board.sitter;

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
@Table(name = "SITTER")
public class Sitter {

    @Id
    @Column(name = "SITTER_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sitterSequence")
    @SequenceGenerator(name = "sitterSequence", sequenceName = "SEQ_SITTER" ,allocationSize = 1)
    private int sitterCode;

    @Column(name = "SITTER_TITLE")
    private String sitterTitle;

    @Column(name = "SITTER_LOC")
    private String sitterLoc;

    @Column(name = "SITTER_PRICE")
    private int sitterPrice;

    @Column(name = "SITTER_DESC")
    private String sitterDesc;

    @Column(name = "SITTER_IMG")
    private String sitterImg;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Member member;
}
