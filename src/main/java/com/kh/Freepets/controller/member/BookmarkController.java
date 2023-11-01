package com.kh.Freepets.controller.member;

import com.kh.Freepets.domain.board.BoardDTO;
import com.kh.Freepets.domain.board.community.Community;
import com.kh.Freepets.domain.board.information.HospitalReview;
import com.kh.Freepets.domain.board.notice.Notice;
import com.kh.Freepets.domain.board.sitter.Sitter;
import com.kh.Freepets.domain.member.Bookmark;
import com.kh.Freepets.BoardType;
import com.kh.Freepets.domain.member.BookmarkDTO;
import com.kh.Freepets.domain.member.Member;
import com.kh.Freepets.domain.member.MemberDTO;
import com.kh.Freepets.security.TokenProvider;
import com.kh.Freepets.service.board.community.CommunityService;
import com.kh.Freepets.service.board.information.HospitalReviewService;
import com.kh.Freepets.service.board.notice.NoticeService;
import com.kh.Freepets.service.board.sitter.SitterService;
import com.kh.Freepets.service.member.BookmarkService;
import com.kh.Freepets.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/*")
@CrossOrigin
public class BookmarkController
{
    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private SitterService sitterService;

    @Autowired
    private HospitalReviewService hospitalReviewService;


    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/bookmark")
    public ResponseEntity<List<Bookmark>> showAll()
    {  // 모든 북마크 가져오기
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.showAll());
    }


    @GetMapping("/bookmark/{token}")
    public ResponseEntity<List<BookmarkDTO>> show(@PathVariable String token)
    {  // 특정 유저 북마크 가져오기
        String userId = tokenProvider.validateAndGetUserId(token);
        List<Bookmark> list = bookmarkService.findByMemberId(userId);
        List<BookmarkDTO> responseDTO = new ArrayList<BookmarkDTO>();

        for (Bookmark item : list)
        {
            BoardDTO boardDTO = null;
            MemberDTO mDTO = null;
            int bookmarkCode = item.getBookmarkCode();
            switch (item.getBoardCode())
            {

                case 1:
                    log.info("1번 스위치");
                    Community community = communityService.showCommon(item.getPostCode());

                    mDTO = MemberDTO.builder()
                            .nickname(community.getMember().getNickname())
                            .memberImg(community.getMember().getMemberImg())
                            .build();

                    boardDTO = BoardDTO.builder()
                            .memberDTO(mDTO)
                            .title(community.getCommonTitle())
                            .date(community.getCommonDate())
                            .boardCode(community.getCommonCode())
                            .postPath("/community/common/commonview/" + item.getPostCode() + "/undefined")
                            .build();
                    break;
                case 2:
                    //lost
                    break;
                case 3:
                    log.info("3번 스위치");
                    Sitter sitter = sitterService.show(item.getPostCode());
                    mDTO = MemberDTO.builder()
                            .nickname(sitter.getMember().getNickname())
                            .memberImg(sitter.getMember().getMemberImg())
                            .build();

                    boardDTO = BoardDTO.builder()
                            .memberDTO(mDTO)
                            .postPath("../sitter/view/" + item.getPostCode())
                            .title(sitter.getSitterTitle())
                            .date(null)
                            .boardCode(sitter.getSitterCode())
                            .build();

                    break;
                case 4:
                    log.info("4번 스위치");
                    HospitalReview hospitalReview = hospitalReviewService.show(item.getPostCode());
                    mDTO = MemberDTO.builder()
                            .nickname(hospitalReview.getMember().getNickname())
                            .memberImg(hospitalReview.getMember().getMemberImg())
                            .build();

                    boardDTO = BoardDTO.builder()
                            .memberDTO(mDTO)

                            .title(hospitalReview.getHospitalReviewTitle())
                            .date(hospitalReview.getHospitalReviewDate())
                            .boardCode(hospitalReview.getHospitalReviewCode())
                            .postPath("../hospital/view/" + item.getPostCode())
                            .build();

                    break;
                case 5:
                    log.info("5번 스위치");
                    Notice notice = noticeService.show(item.getPostCode());
                    mDTO = MemberDTO.builder()
                            .nickname(notice.getMember().getNickname())
                            .memberImg(notice.getMember().getMemberImg())
                            .build();

                    boardDTO = BoardDTO.builder()
                            .memberDTO(mDTO)
                            .title(notice.getNoticeTitle())
                            .date(notice.getNoticeDate())
                            .boardCode(notice.getNoticeCode())
                            .postPath("../notice/noticeView/" + item.getPostCode())
                            .build();
                    break;
                case 6:
                    //customerService
                    break;
                default:
                    return ResponseEntity.ok().body(null);

            }

            BookmarkDTO bookmarkDTO = BookmarkDTO.builder()
                    .bookmarkCode(bookmarkCode)
                    .boardDTO(boardDTO)
                    .boardName(BoardType.getTypeName(BoardType.getType(item.getBoardCode())))
                    .nickname(mDTO.getNickname())
                    .postCode(boardDTO.getBoardCode())
                    .url(boardDTO.getPostPath())
                    .build();
//            log.info(bookmarkDTO.toString());
            responseDTO.add(bookmarkDTO);
        }

        log.info(responseDTO.toString());

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

//    // 특정 유저의 특정 게시판 가져오기
//    @GetMapping("/api/bookmark/{id}/{board_name}")
//    public ResponseEntity<List<Bookmark>> showByBoard(@PathVariable String id, @PathVariable String board_name)
//    {// 게시판 별 북마크 보기 프론트에서 board_name -> String 문자열로 넘길때
//        List<Bookmark> lists = bookmarkService.findByBoard(id, BoardType.getType(board_name));
//        return ResponseEntity.status(HttpStatus.OK).body(lists);
//    }

    @GetMapping("/bookmark/{id}/{boardName}")
    public ResponseEntity<List<Bookmark>> showByBoard(@PathVariable String id, @PathVariable String boardName)
    {// 게시판 별 북마크 보기 프론트에서 code -> int값으로 넘길때

        BoardType boardType = BoardType.getType(boardName);
        int typeCode = BoardType.getTypeCode(boardType);

        if (typeCode > 0)
        {// 게시판 이름 받아서 Board Type으로 바꿔주고 code값으로 리턴 받아서 양수 인경우에는 알맞는 데이터 들어옴!
            List<Bookmark> lists = bookmarkService.findBy_id_code(id, typeCode);
            return ResponseEntity.ok().body(lists);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/bookmark")
    public ResponseEntity<Bookmark> create(@RequestBody BookmarkDTO bookmarkDTO)
    {
log.info(bookmarkDTO.toString());

        String userId = tokenProvider.validateAndGetUserId(bookmarkDTO.getToken());

        log.info(userId);
        Member member = memberService.findByIdUser(userId);


        int boardCode = BoardType.getTypeCode(BoardType.getType(bookmarkDTO.getBoardName()));

        List<Bookmark> list = bookmarkService.findBy_id_code(userId, boardCode);


        for (Bookmark bm : list)
        {
            // 게시글이 이미 북마크에 있음;
            if (bm.getPostCode() == bookmarkDTO.getPostCode())
            {
                log.info("이미 들어가 있음..");
                return ResponseEntity.ok().body(null);
            }
        }


        Bookmark bookmark = Bookmark.builder()
                .boardCode(boardCode)
                .postCode(bookmarkDTO.getPostCode())
                .member(member)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.create(bookmark));
    }


    @DeleteMapping("/bookmark/{code}")
    public ResponseEntity<Bookmark> delete(@PathVariable int code)
    {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.delete(code));
    }


}
