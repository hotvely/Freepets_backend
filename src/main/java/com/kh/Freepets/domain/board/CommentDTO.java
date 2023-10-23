package com.kh.Freepets.domain.board;

import com.kh.Freepets.domain.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String token;       //댓글 달려고 하는 유저 토큰
    private String boardName;   //댓글 달려고 하는 게시판 이름
    // boardName이 필요한 이유가 CommentDTO를 공용으로 사용하려고 하기 때문에 어떤 게시판의 몇번 게시글인지 알아야 하기 때문임!!!
    // lost(분실신고 게시판) , common(지우님 자유게시판), sitter(시터), 처럼 정해 놓고 받으면 될것 같음..
    // 숫자로 받아도 되기는 하는데, 몇번이 어떤 게시판인지 자꾸 까먹으니까 걍 명시적으로 받는게 좋아보임

    private int commentCode;      //댓글 코드
    private int postCode;       //댓글 달려고 하는 게시글 번호
    private String commentDesc; //댓글 내용
    private int parentCommentCode;      // 부모 댓글 코드

    private MemberDTO memberDTO;

}
