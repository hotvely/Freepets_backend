package com.kh.Freepets.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@DynamicInsert    // <- 혹시나 날짜나 Default 값 들어가야 되면 해제..
@Builder
public class Notification {

    @Id
    @Column(name = "NOTI_CODE")
    private int notiCode;       //알림 고유 코드

    @Column(name = "ID")    //<- 토큰을 넣어서 해야하나.. ID그냥 넣어야 하나 고민중!
    private String id;          //내한테 알람 와야 하니까 내 아이디도 들고 있어야함; 굳이 연결할 필요는 없;

    @Column(name = "NOTI_POSTCODE")
    private int postCode;   //내가 쓴 게시글 <- 누가 내 게시글에 댓글 달면 뭔지 알아야함


    @Column(name = "NOTI_COMMENTCODE")
    private int commentCode; //내가 단 댓글  <- 누군가 내 댓글에 댓글을 달면 뿌려야 해서

    @Column(name = "NOTI_URL")
    private String url;         //걍 알림 누르면 이동한 주소..ㅋㅋㅋㅋ

}
