package com.kh.Freepets.domain.board;

import com.kh.Freepets.domain.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO
{
    private int boardCode;          //게시판 코드 번호
    private String title;           //게시글 제목
    private Date commonDate;        //게시글 작성 날짜
    private String desc;            //게시글 설명(내용)
    private int viewCount;          //게시글 조회 수
    private int likeCount;          //게시글 좋아요 수
    private int commentCount;       //게시글 댓글 수
    private String uploadfileUrl;   //게시글 첨부 upload file url (사진,동영상등)
    private char deleteYN;          //게시글이 삭제 되었나? (됬으면 Y, 아니면 N)

    private MemberDTO memberDTO;    //게시글에서 들고 있어야 하는 멤버 관련 정보?


    //이제 각자 테이블 에서 프론트로 데이터 밀어 넣으면서.. 필요한 데이터 추가 해주시면 됩니다.
    // 도경

    // 지우


}
