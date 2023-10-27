------------------------- SEQUENCE 삭제 코드 --------------------------

--- 제거 예정 sequence (이미 있는 거 지울 때 사용)
--DROP SEQUENCE SEQ_SALESHOP;
DROP SEQUENCE SEQ_HOSPITAL;
--DROP SEQUENCE SEQ_FREEMARKET_REVIEW;
--DROP SEQUENCE SEQ_FREEMARKET;
--DROP SEQUENCE SEQ_PR_COMMENT;
--DROP SEQUENCE SEQ_PR_LIKE;
--DROP SEQUENCE SEQ_PRODUCT_REVIEW;
--DROP SEQUENCE SEQ_VI_COMMENT;
--DROP SEQUENCE SEQ_VI_LIKE;
--DROP SEQUENCE SEQ_VIDEO_INFO;
--DROP SEQUENCE SEQ_MEDIA_COMMENT;
--DROP SEQUENCE SEQ_MEDIA;
---------------------------------------------

---- notice
DROP SEQUENCE SEQ_EVENT;
DROP SEQUENCE SEQ_NOTICE;
DROP SEQUENCE SEQ_NOTICE_LIKE;
DROP SEQUENCE SEQ_NOTICE_COMMENT;

---- sitter
DROP SEQUENCE SEQ_SITTER_REVIEW;
DROP SEQUENCE SEQ_SITTER;

----- information
DROP SEQUENCE SEQ_HR_COMMENT;
DROP SEQUENCE SEQ_HR_LIKE;
DROP SEQUENCE SEQ_HOSPITAL_REVIEW;

----- community
DROP SEQUENCE SEQ_LOST;
DROP SEQUENCE SEQ_LOST_LIKE;
DROP SEQUENCE SEQ_LOST_COMMENT;

DROP SEQUENCE SEQ_COMMON_COMMENT;
DROP SEQUENCE SEQ_COMMON;
DROP SEQUENCE SEQ_COMMON_LIKE;

----- NOTIFICATION
DROP SEQUENCE SEQ_NOTIFICATION;

----- member
DROP SEQUENCE SEQ_BOOKMARK;

------------------------- SEQUENCE 삭제 코드 --------------------------


--------------------------- TABLE 삭제 코드 ----------------------------

--- 제거 예정 drop (이미 있는 거 지울 때 사용 순서 맞춰 놨음)

--DROP TABLE MEDIA_COMMENT;
--DROP TABLE MEDIA_LIKE;
--DROP TABLE MEDIA;
--DROP TABLE PR_LIKE;
--DROP TABLE PR_COMMENT;
--DROP TABLE PRODUCT_REVIEW;
--DROP TABLE VI_LIKE;
--DROP TABLE VI_COMMENT;
--DROP TABLE VIDEO_INFO;
----------------------------------------



---- sitter
DROP TABLE SITTER_REVIEW;
DROP TABLE SITTER;

---- community
DROP TABLE COMMON_COMMENT;
DROP TABLE COMMON_LIKE;
DROP TABLE COMMON;

DROP TABLE LOST_LIKE;
DROP TABLE LOST_COMMENT;
DROP TABLE LOST;

---- information
DROP TABLE HR_LIKE;
DROP TABLE HR_COMMENT;
DROP TABLE HOSPITAL_REVIEW;

DROP TABLE HOSPITAL;

---- notice
DROP TABLE NOTICE_COMMENT;
DROP TABLE NOTICE_LIKE;
DROP TABLE NOTICE;
DROP TABLE EVENT;

---- member
DROP TABLE BOOKMARK;
DROP TABLE NOTIFICATION;
DROP TABLE MEMBER;


--------------------------- TABLE 삭제 코드 ----------------------------


------------------------------------------------------------------ USER TABLE 관련
CREATE TABLE MEMBER
(
    ID VARCHAR2(30) ,
    PASSWORD VARCHAR2(250) NOT NULL,
    NAME VARCHAR2(30) NOT NULL,
    BIRTH DATE,
    GENDER CHAR,
    PHONE VARCHAR2(30),
    ADDRESS VARCHAR2(50),
    NICKNAME VARCHAR2(20) NOT NULL,
    EMAIL VARCHAR2(30),
    CREATE_ACCOUNT_DATE DATE DEFAULT SYSDATE,
    DELETE_ACCOUNT_YN CHAR DEFAULT 'N' CHECK(DELETE_ACCOUNT_YN IN ('Y','N')),
    AUTHORITY VARCHAR2(10) DEFAULT 'USER' CHECK(AUTHORITY IN ('ADMIN','USER')),
    MEMBER_IMG VARCHAR2(500),
    MEMBER_INFO VARCHAR2(1000),
    
    CONSTRAINT ID_PK PRIMARY KEY(ID)
);  
--INSERT INTO MEMBER(id,password,name,birth,gender,phone,address,nickname,email, member_img, member_info) 
--VALUES('test','test','testName',sysdate, 'm', '010-0000-0000','test Address','test','test@naver.com',NULL,NULL);
--INSERT INTO MEMBER(id,password,name,birth,gender,phone,address,nickname,email,  authority, member_img, member_info) 
--VALUES('admin','admin','admin',sysdate, 'f', '010-1111-1111','admin Address','admin','admin@naver.com','ADMIN',NULL,NULL);

CREATE TABLE BOOKMARK
(
    BOOKMARK_CODE NUMBER PRIMARY KEY,    
    BOARD_CODE NUMBER,
    POST_CODE NUMBER,    
    BOOKMARK_DATE DATE DEFAULT SYSDATE,
    ID VARCHAR2(100)
);

CREATE TABLE NOTIFICATION
(
      NOTI_CODE NUMBER PRIMARY KEY,              -- 알림 테이블 고유 코드
      ID VARCHAR2(30),                         -- 저장할 멤버 아이디
      NOTI_BOARDCODE NUMBER,
      NOTI_POSTCODE NUMBER,
      NOTI_CHILD_COMMENTCODE NUMBER,
      NOTI_PARENT_COMMENTCODE NUMBER,
      NOTI_URL VARCHAR2(500)
      

);
ALTER TABLE NOTIFICATION ADD CONSTRAINT NOTIFICATION_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER; 


CREATE SEQUENCE SEQ_NOTIFICATION;
CREATE SEQUENCE SEQ_NOTICODE;
CREATE SEQUENCE SEQ_BOOKMARK;


------------------------------------------------------------------ Notice 관련
CREATE TABLE NOTICE(
 NOTICE_CODE NUMBER PRIMARY KEY,
 NOTICE_TITLE VARCHAR2(300)NOT NULL,
 NOTICE_ADDFILE_URL VARCHAR2(1000),
 NOTICE_DATE DATE DEFAULT SYSDATE,
 NOTICE_VIEWS NUMBER DEFAULT 0,
 NOTICE_DESC VARCHAR2(1000),

 NOTICE_LIKE NUMBER DEFAULT 0,
 NOTICE_COMMENT_COUNT NUMBER DEFAULT 0,
 ID VARCHAR2(100)
);

CREATE TABLE NOTICE_LIKE(
    NOTICE_LIKE_CODE NUMBER PRIMARY KEY,
    NOTICE_LIKE_DATE DATE DEFAULT SYSDATE,
    NOTICE_CODE NUMBER,
    ID VARCHAR2(200)
);

CREATE TABLE NOTICE_COMMENT(
 NOTICE_COMMENT_CODE NUMBER PRIMARY KEY,
 NOTICE_COMMENT_DESC VARCHAR2(500),
 NOTICE_COMMENT_DATE DATE DEFAULT SYSDATE,
 NOTICE_COMMENT_CODE_SUPER NUMBER,

 NOTICE_CODE NUMBER,
 ID VARCHAR2(100)
);

ALTER TABLE NOTICE ADD CONSTRAINT NOTICE_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

ALTER TABLE NOTICE_LIKE ADD CONSTRAINT NOTICE_LIKE_CODE_FK FOREIGN KEY (NOTICE_CODE)REFERENCES NOTICE ON DELETE CASCADE;
ALTER TABLE NOTICE_LIKE ADD CONSTRAINT NOTICE_LIKE_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

ALTER TABLE NOTICE_COMMENT ADD CONSTRAINT NOTICE_COMMENT_CODE_FK FOREIGN KEY (NOTICE_CODE)REFERENCES NOTICE ON DELETE CASCADE;
ALTER TABLE NOTICE_COMMENT ADD CONSTRAINT NOTICE_COMMENT_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

CREATE SEQUENCE SEQ_NOTICE;
CREATE SEQUENCE SEQ_NOTICE_LIKE;
CREATE SEQUENCE SEQ_NOTICE_COMMENT;




------------------------------------------------------------------ SITTER TABLE 관련
CREATE TABLE SITTER(
    SITTER_CODE NUMBER PRIMARY KEY,
    SITTER_TITLE VARCHAR2(200) NOT NULL,
    SITTER_LOC VARCHAR2(200) NOT NULL,
    SITTER_PRICE INT NOT NULL,
    SITTER_RATINGS NUMBER,
    SITTER_DESC VARCHAR2(500),
    SITTER_IMG VARCHAR2(300),
    ID VARCHAR2(100)
);

CREATE TABLE SITTER_REVIEW(
    SITTER_REVIEW_CODE NUMBER PRIMARY KEY,
    SITTER_REVIEW_DESC VARCHAR(300),
    SITTER_REVIEW_RATINGS INT,
    ID VARCHAR2(100),
    SITTER_CODE NUMBER
);

CREATE SEQUENCE SEQ_SITTER;
CREATE SEQUENCE SEQ_SITTER_REVIEW;

ALTER TABLE SITTER_REVIEW ADD CONSTRAINT SITTER_REVIEW_SITTER_CODE_FK FOREIGN KEY(SITTER_CODE) REFERENCES SITTER;

CREATE TABLE CHATTING (
    CHATTING_CODE NUMBER PRIMARY KEY,
    SENDER VARCHAR2(50),
    RECEIVER VARCHAR2(50),
    CHATTING_DATE DATE DEFAULT SYSDATE,
    CHATTING_CONTENT VARCHAR2(1000)
);

ALTER TABLE CHATTING ADD CONSTRAINT SENDER_FK FOREIGN KEY(SENDER) REFERENCES MEMBER(ID);
ALTER TABLE CHATTING ADD CONSTRAINT RECEIVER_FK FOREIGN KEY(RECEIVER) REFERENCES MEMBER(ID);

CREATE SEQUENCE SEQ_CHATTING;

----------------------------------------------------------------- COMMUNITY TABLE 관련
CREATE TABLE COMMON(
 COMMON_CODE NUMBER PRIMARY KEY,
 COMMON_DATE DATE DEFAULT SYSDATE,
 COMMON_TITLE VARCHAR2(300)NOT NULL,
 COMMON_DESC VARCHAR2(1000),
 COMMON_ADD_FILE_URL VARCHAR2(500),
 COMMON_YOUTUBE_URL VARCHAR2(500),
 COMMON_VIEW_COUNT NUMBER DEFAULT 0,
 COMMON_LIKE_COUNT NUMBER DEFAULT 0,
 COMMON_COMMENT_COUNT NUMBER DEFAULT 0,
 --COMMON_REPORT_YN CHAR DEFAULT 'N' CHECK(COMMON_REPORT_YN IN ('Y','N')) NOT NULL,
 ID VARCHAR2(100)
);

ALTER TABLE COMMON ADD CONSTRAINT COMMON_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;
ALTER TABLE COMMON MODIFY COMMON_DESC VARCHAR2(100000);


CREATE TABLE COMMON_COMMENT(
 C_COMMENT_CODE NUMBER PRIMARY KEY,
 C_COMMENT_DESC VARCHAR2(500),
 C_COMMENT_DATE DATE DEFAULT SYSDATE,
 C_COMMENT_CODE_SUPER NUMBER,
 C_COMMENT_ADD_FILE_URL VARCHAR2(500),
 --C_COMMENT_REPORT_YN CHAR DEFAULT 'N' CHECK(C_COMMENT_REPORT_YN IN ('Y','N')) NOT NULL,
 COMMON_CODE NUMBER,
 ID VARCHAR2(100)
);

ALTER TABLE COMMON_COMMENT ADD CONSTRAINT COMMON_COMMENT_CODE_FK FOREIGN KEY(COMMON_CODE)REFERENCES COMMON;
ALTER TABLE COMMON_COMMENT ADD CONSTRAINT COMMON_COMMENT_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

CREATE TABLE COMMON_LIKE(
 C_LIKE_CODE NUMBER PRIMARY KEY,
 COMMON_CODE NUMBER,
 ID VARCHAR2(100)
);

ALTER TABLE COMMON_LIKE ADD CONSTRAINT COMMON_LIKE_CODE_FK FOREIGN KEY(COMMON_CODE) REFERENCES COMMON;
ALTER TABLE COMMON_LIKE ADD CONSTRAINT COMMON_LIKE_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

CREATE TABLE LOST(
 LOST_CODE NUMBER PRIMARY KEY,
 LOST_TITLE VARCHAR2(300)NOT NULL,
 LOST_ADDFILE_URL VARCHAR2(1000),
 LOST_DATE DATE DEFAULT SYSDATE,
 LOST_VIEWS NUMBER DEFAULT 0,
 LOST_DESC VARCHAR2(1000),

 LOST_LIKE NUMBER DEFAULT 0,
 LOST_COMMENT_COUNT NUMBER DEFAULT 0,
 ID VARCHAR2(100)
);

CREATE TABLE LOST_LIKE(
    LOST_LIKE_CODE NUMBER PRIMARY KEY,
    LOST_LIKE_DATE DATE DEFAULT SYSDATE,
    LOST_CODE NUMBER,
    ID VARCHAR2(200)
);

CREATE TABLE LOST_COMMENT(
 L_COMMENT_CODE NUMBER PRIMARY KEY,
 L_COMMENT_DESC VARCHAR2(500),
 L_COMMENT_DATE DATE DEFAULT SYSDATE,
 L_COMMENT_CODE_SUPER NUMBER,


 LOST_CODE NUMBER,
 ID VARCHAR2(100)
);

ALTER TABLE LOST ADD CONSTRAINT LOST_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

ALTER TABLE LOST_LIKE ADD CONSTRAINT LOST_LIKE_CODE_FK FOREIGN KEY (LOST_CODE)REFERENCES LOST;
ALTER TABLE LOST_LIKE ADD CONSTRAINT LOST_LIKE_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

ALTER TABLE LOST_COMMENT ADD CONSTRAINT LOST_COMMENT_CODE_FK FOREIGN KEY (LOST_CODE)REFERENCES LOST;
ALTER TABLE LOST_COMMENT ADD CONSTRAINT LOST_COMMENT_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

CREATE SEQUENCE SEQ_LOST;
CREATE SEQUENCE SEQ_LOST_LIKE;
CREATE SEQUENCE SEQ_LOST_COMMENT;

CREATE SEQUENCE SEQ_COMMON;
CREATE SEQUENCE SEQ_COMMON_COMMENT;
CREATE SEQUENCE SEQ_COMMON_LIKE;
------------------------------------------------------------------------- INFORMATION TABLE 관련
CREATE TABLE HOSPITAL (
    HOSPITAL_CODE NUMBER PRIMARY KEY,
    HOSPITAL_NAME VARCHAR2(30) NOT NULL,
    HOSPITAL_ADDRESS VARCHAR2(100)
);

CREATE TABLE HOSPITAL_REVIEW (
    HOSPITAL_REVIEW_CODE NUMBER PRIMARY KEY,
    ID VARCHAR2(30),
    HOSPITAL_NAME VARCHAR2(30) NOT NULL,
    HOSPITAL_ADDRESS VARCHAR2(100),
    HOSPITAL_REVIEW_VIEWS NUMBER DEFAULT 0,
    HOSPITAL_REVIEW_DATE DATE DEFAULT SYSDATE,
    HOSPITAL_REVIEW_TITLE VARCHAR2(200) NOT NULL,
    HOSPITAL_REVIEW_DESC VARCHAR2(1000),
    HOSPITAL_REVIEW_FILE_URL VARCHAR2(1000),
    HOSPITAL_REVIEW_URL VARCHAR2(300),
    HOSPITAL_REVIEW_LIKE NUMBER DEFAULT 0,
    HOSPITAL_REVIEW_COMMENT_COUNT NUMBER DEFAULT 0,
    HOSPITAL_REVIEW_REPORT_YN CHAR(1) DEFAULT 'N' CHECK(HOSPITAL_REVIEW_REPORT_YN IN ('Y', 'N')) NOT NULL
);

CREATE TABLE HR_COMMENT (
    HR_COMMENT_CODE NUMBER PRIMARY KEY,
    HOSPITAL_REVIEW_CODE NUMBER,
    ID VARCHAR2(30),
    HR_COMMENT_DATE DATE DEFAULT SYSDATE,
    HR_COMMENT_DESC VARCHAR2(500) NOT NULL,
    HR_COMMENT_IMG VARCHAR2(200),
    SUPER_HR_COMMENT_CODE NUMBER,
    HR_COMMENT_REPORT_YN CHAR(1) DEFAULT 'N' CHECK(HR_COMMENT_REPORT_YN IN ('Y', 'N')) NOT NULL
);

CREATE TABLE HR_LIKE(
    HR_LIKE_CODE NUMBER PRIMARY KEY,
    HOSPITAL_REVIEW_CODE NUMBER,
    ID VARCHAR2(30)
);

CREATE SEQUENCE SEQ_HOSPITAL_REVIEW;
CREATE SEQUENCE SEQ_HR_COMMENT;
CREATE SEQUENCE SEQ_HR_LIKE;
CREATE SEQUENCE SEQ_HOSPITAL;

ALTER TABLE HOSPITAL_REVIEW ADD CONSTRAINT HOSPITAL_REVIEW_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;

ALTER TABLE HR_COMMENT ADD CONSTRAINT HR_COMMENT_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;
ALTER TABLE HR_COMMENT ADD CONSTRAINT HR_COMMENT_HOSPITAL_REVIEW_CODE_FK FOREIGN KEY(HOSPITAL_REVIEW_CODE) REFERENCES HOSPITAL_REVIEW;

ALTER TABLE HR_LIKE ADD CONSTRAINT HR_LIKE_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;
ALTER TABLE HR_LIKE ADD CONSTRAINT HR_LIKE_HOSPITAL_REVIEW_CODE_FK FOREIGN KEY(HOSPITAL_REVIEW_CODE) REFERENCES HOSPITAL_REVIEW;



CREATE TABLE EVENT(
-- 아마 공공데이터 쪽 배우게 되면 이벤트 정보 같은거 존재 하면 가져다 쓸 것 같긴 함;
 EVENT_CODE NUMBER PRIMARY KEY,
 EVENT_YEAR NUMBER NOT NULL,
 EVENT_MONTH NUMBER NOT NULL,
 EVENT_DAY NUMBER NOT NULL,
 EVENT_TITLE VARCHAR2(300),     -- 이벤트 게시글 제목
 EVENT_DESC VARCHAR2(1000),     -- 이벤트 게시글 설명
 EVENT_LINK_ADDRESS VARCHAR2(500),  -- 이벤트 게시글안에 들어갈 주소
 EVENT_PRICE NUMBER,            -- 이벤트 입장비용? 가격?
 
 ID VARCHAR2(100)
);

CREATE SEQUENCE SEQ_EVENT;
--------------------------------------------------------------------------------------------

commit;
