------------------------- SEQUENCE 삭제 코드 --------------------------

--- 제거 예정 sequence (이미 있는 거 지울 때 사용)
DROP SEQUENCE SEQ_SALESHOP;
DROP SEQUENCE SEQ_HOSPITAL;
DROP SEQUENCE SEQ_FREEMARKET_REVIEW;
DROP SEQUENCE SEQ_FREEMARKET;
DROP SEQUENCE SEQ_PR_COMMENT;
DROP SEQUENCE SEQ_PR_LIKE;
DROP SEQUENCE SEQ_PRODUCT_REVIEW;
DROP SEQUENCE SEQ_VI_COMMENT;
DROP SEQUENCE SEQ_VI_LIKE;
DROP SEQUENCE SEQ_VIDEO_INFO;
DROP SEQUENCE SEQ_MEDIA_COMMENT;
DROP SEQUENCE SEQ_MEDIA;
---------------------------------------------

---- notice
DROP SEQUENCE SEQ_EVENT;
DROP SEQUENCE SEQ_NOTICE;

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

----- member
DROP SEQUENCE SEQ_BOOKMARK;

------------------------- SEQUENCE 삭제 코드 --------------------------


--------------------------- TABLE 삭제 코드 ----------------------------

--- 제거 예정 drop (이미 있는 거 지울 때 사용 순서 맞춰 놨음)
DROP TABLE FREEMARKET_REVIEW;
DROP TABLE FREEMARKET;
DROP TABLE MEDIA_COMMENT;
DROP TABLE MEDIA_LIKE;
DROP TABLE MEDIA;
DROP TABLE PR_LIKE;
DROP TABLE PR_COMMENT;
DROP TABLE PRODUCT_REVIEW;
DROP TABLE VI_LIKE;
DROP TABLE VI_COMMENT;
DROP TABLE VIDEO_INFO;
----------------------------------------

---- notice
DROP TABLE NOTICE;
DROP TABLE EVENT;

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

---- member
DROP TABLE BOOKMARK;
DROP TABLE MEMBER;

--------------------------- TABLE 삭제 코드 ----------------------------


------------------------------------------------------------------ USER TABLE 관련
CREATE TABLE MEMBER
(
    ID VARCHAR2(30) ,
    PASSWORD VARCHAR2(50) NOT NULL,
    NAME VARCHAR2(30) NOT NULL,
    BIRTH DATE,
    GENDER CHAR,
    PHONE VARCHAR2(30),
    ADDRESS VARCHAR2(50),
    NICKNAME VARCHAR2(20) NOT NULL,
    EMAIL VARCHAR2(30),
    CREATE_ACCOUNT_DATE DATE DEFAULT SYSDATE,
    DELETE_ACCOUNT_YN CHAR DEFAULT 'N' CHECK(DELETE_ACCOUNT_YN IN ('Y','N')) NOT NULL,
    AUTHORITY VARCHAR2(10) DEFAULT 'USER' CHECK(AUTHORITY IN ('ADMIN','USER')),
    MEMBER_IMG VARCHAR2(500),
    MEMBER_INFO VARCHAR2(1000),
    
    CONSTRAINT ID_PK PRIMARY KEY(ID)
);  
INSERT INTO MEMBER(id,password,name,birth,gender,phone,address,nickname,email, member_img, member_info) 
VALUES('test','test','testName',sysdate, 'm', '010-0000-0000','test Address','test','test@naver.com',NULL,NULL);
INSERT INTO MEMBER(id,password,name,birth,gender,phone,address,nickname,email,  authority, member_img, member_info) 
VALUES('admin','admin','admin',sysdate, 'f', '010-1111-1111','admin Address','admin','admin@naver.com','ADMIN',NULL,NULL);

CREATE TABLE BOOKMARK
(
    BOOKMARK_CODE NUMBER PRIMARY KEY,    
    BOARD_CODE NUMBER,
    POST_CODE NUMBER,    
    BOOKMARK_DATE DATE DEFAULT SYSDATE,
    ID VARCHAR2(100)
);

CREATE SEQUENCE SEQ_BOOKMARK;

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

----------------------------------------------------------------- COMMUNITY TABLE 관련
CREATE TABLE COMMON(
 COMMON_CODE NUMBER PRIMARY KEY,
 COMMON_DATE DATE DEFAULT SYSDATE,
 COMMON_VIEWS NUMBER DEFAULT 0,
 COMMON_TITLE VARCHAR2(300)NOT NULL,
 COMMON_DESC VARCHAR2(1000),
 COMMON_ADD_FILE_URL VARCHAR2(500),
 COMMON_YOUTUBE_URL VARCHAR2(500),
 --COMMON_REPORT_YN CHAR DEFAULT 'N' CHECK(COMMON_REPORT_YN IN ('Y','N')) NOT NULL,
 ID VARCHAR2(100)
);

ALTER TABLE COMMON ADD CONSTRAINT COMMON_ID_FK FOREIGN KEY(ID) REFERENCES MEMBER;


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
 LOST_URL VARCHAR2(1000),
 LOST_DATE DATE DEFAULT SYSDATE,
 LOST_VIEWS NUMBER DEFAULT 0,
 LOST_DESC VARCHAR2(1000),
 LOST_REPORT_YN CHAR DEFAULT 'N' CHECK(LOST_REPORT_YN IN ('Y','N')) NOT NULL,
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
 L_COMMENT_IMG VARCHAR2(1000),
 L_COMMENT_REPORT_YN CHAR DEFAULT 'N' CHECK(L_COMMENT_REPORT_YN IN ('Y','N')) NOT NULL,
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

CREATE SEQUENCE SEQ_MEDIA;
CREATE SEQUENCE SEQ_MEDIA_COMMENT;
CREATE SEQUENCE SEQ_COMMON;
CREATE SEQUENCE SEQ_COMMON_COMMENT;
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

--------------------------------------------------------------------------- NOTICE TABLE 관련
CREATE TABLE NOTICE (
NOTICE_CODE NUMBER PRIMARY KEY,
NOTICE_TITLE VARCHAR2(100),
NOTICE_CONTENTS VARCHAR2(500),
NOTICE_VIEWS NUMBER,
NOTICE_DATE VARCHAR2(100) NOT NULL,
ID VARCHAR2(100)
);

CREATE TABLE EVENT(
-- 아마 공공데이터 쪽 배우게 되면 이벤트 정보 같은거 존재 하면 가져다 쓸 것 같긴 함;
 EVENT_CODE NUMBER PRIMARY KEY,
 EVENT_TITLE VARCHAR2(300),
 EVENT_CONTENTS VARCHAR2(500),
 EVENT_DATE VARCHAR2(100) NOT NULL,
 EVENT_LINK_ADDRESS VARCHAR2(500),
 
 ID VARCHAR2(100)
);

CREATE SEQUENCE SEQ_EVENT;
CREATE SEQUENCE SEQ_NOTICE;
--------------------------------------------------------------------------------------------

commit;
