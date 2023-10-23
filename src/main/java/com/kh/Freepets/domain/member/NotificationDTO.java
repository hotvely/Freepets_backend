package com.kh.Freepets.domain.member;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private int code;       //고유 코드..
    private String id;          //내한테 알람 와야 하니까 내 아이디도 들고 있어야함; 굳이 연결할 필요는 없;

    private int boardCode;

    private int postCode;   //내가 쓴 게시글 <- 누가 내 게시글에 댓글 달면 뭔지 알아야함

    private int childCommentCode; //내가 단 댓글  <- 누군가 내 댓글에 댓글을 달면 뿌려야 해서
    private int parentCommentCode;  // 0번이면...

    private String url;         //걍 알림 누르면 이동한 주소..ㅋㅋㅋㅋ

    private BoardDTO boardDTO;

    private CommentDTO childComment;
    private CommentDTO parentComment;

    // 받기용 token
    private String token;

}
