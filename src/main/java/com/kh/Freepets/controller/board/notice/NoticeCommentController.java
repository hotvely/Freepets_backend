package com.kh.Freepets.controller.board.notice;

import com.kh.Freepets.domain.board.CommentDTO;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.notice.NoticeComment;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.notice.NoticeCommentService;
import com.kh.Freepets.service.board.notice.NoticeService;
import com.kh.Freepets.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin
public class NoticeCommentController
{

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private NoticeCommentService noticecommentService;

    @Autowired
    private NoticeService noticeservice;

    @Autowired
    private MemberService memberService;


    // 게시물 1개에 따른 댓글 전체 보기 GET - http://localhost:8080/api/notice/1/comment
    //SELECT * FROM NOTICE_COMMENT WHERE NOTICE_CODE = ?
    @GetMapping("/notice/{noticeCode}/comment")
    private ResponseEntity<List<NoticeComment>> noticeCommentList(@PathVariable int noticeCode)
    {
        List<NoticeComment> list = noticecommentService.findByNoticeCode(noticeCode);
        log.info(list.toString());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    //분실신고 게시글 댓글 추가하기 POST - http://localhost:8080/api/notice/comment
    // 동시에 댓글 갯수 추가(NoticeDAO - NoticeService 작성)
    @PostMapping("/notice/comment")
    private ResponseEntity<NoticeComment> createNoticeComment(@RequestBody CommentDTO commentDTO)
    {
        log.info("notice 컴트롤러 post :    " + commentDTO.toString());


        if (commentDTO.getBoardName().equals("notice"))
        {
            // 토큰 받아와서 어떤 유저인지 알아 내서 NoticeComment에 넘겨줘야함
            String userId = tokenProvider.validateAndGetUserId(commentDTO.getToken());
            Member member = memberService.findByIdUser(userId);
            // 게시글도 받아와야함 ~
            Notice notice = noticeservice.show(commentDTO.getPostCode());
            // 대댓글인지 확인해서 대댓글아니면 즉, DTO에 parentComment가 0으로 들어오면 -1로 처리.
            if (commentDTO.getParentCommentCode() == 0)
                commentDTO.setParentCommentCode(-1);

            NoticeComment vo = NoticeComment.builder()
                    .noticeCommentDesc(commentDTO.getCommentDesc())
                    .noticeCommentDate(new Date())
                    .notice(notice)
                    .member(member)
                    .noticeCommentCodeSuper(commentDTO.getParentCommentCode()).build();

            return ResponseEntity.status(HttpStatus.OK).body(noticecommentService.create(vo));
        }
        else
        {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


//        noticeservice.updateNoticeCommentCount(vo.getNotice().getNoticeCode());
//        return ResponseEntity.status(HttpStatus.OK).body(noticecomment.create(vo));
    }

    //분실신고 게시글 댓글 수정하기 PUT -http://localhost:8080/api/notice/comment
    @PutMapping("/notice/comment")
    private ResponseEntity<NoticeComment> updateNoticeComment(@RequestBody NoticeComment vo)
    {
        return ResponseEntity.status(HttpStatus.OK).body(noticecommentService.update(vo));
    }

    //분실신고 게시글 댓글 삭제하기 DELETE - http://localhost:8080/api/notice/comment/{lCommentCode}
    // 동시에 댓글 갯수 삭제 (NoticeDAO - NoticeService 작성)
    @DeleteMapping("/notice/comment/{id}")
    private ResponseEntity<NoticeComment> deleteNoticeComment(int lCommentCode)
    {
        NoticeComment target = noticecommentService.showNoticeComment(lCommentCode);

        noticeservice.deleteNoticeCommentCount(target.getNotice().getNoticeCode());
        return ResponseEntity.status(HttpStatus.OK).body(noticecommentService.delete(lCommentCode));
    }

    // 댓글에 사진 첨부하기
    // 댓글에 사진 수정하기
    // 댓글에 사진 삭제하기


}
