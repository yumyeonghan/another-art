DROP TABLE point_history;
DROP TABLE art_hashtag;
DROP TABLE hashtag;
DROP TABLE hashtag_list;
DROP TABLE auction_history;
DROP TABLE purchase_history;
DROP TABLE like_art;
DROP TABLE auction;
DROP TABLE art;
DROP TABLE users;

CREATE TABLE if not exists users(
    user_id BIGINT AUTO_INCREMENT COMMENT '회원_id',
    name VARCHAR(30) NOT NULL COMMENT '회원 이름',
    nickname VARCHAR(30) NOT NULL UNIQUE COMMENT '플랫폼에서 사용할 닉네임',
    login_id VARCHAR(30) NOT NULL UNIQUE COMMENT '로그인 ID',
    login_password VARCHAR(100) NOT NULL COMMENT '로그인 PASSWORD',
    school_name VARCHAR(35) NOT NULL COMMENT '회원이 재학중인 학교이름',
    phone_number VARCHAR(11) NOT NULL UNIQUE COMMENT '회원 전화번호',
    email VARCHAR(50) NOT NULL UNIQUE COMMENT '회원 이메일',
    address VARCHAR(200) NOT NULL COMMENT '회원 주소 (집 주소만)',
    birth DATE NOT NULL COMMENT '회원 생년월일 (yyyy-MM-dd)',

    PRIMARY KEY (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists art(
    art_id BIGINT AUTO_INCREMENT COMMENT '작품_id',
    user_id BIGINT NOT NULL COMMENT '해당 작품의 원작자(user_id)',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '작품 이름(UNIQUE)',
    description VARCHAR(160) NOT NULL COMMENT '작품 설명',
    init_price INT NOT NULL COMMENT '작품 가격(일반 판매는 고정 / 경매는 경매 시작가)',
    sale_type VARCHAR(8) NOT NULL COMMENT '작품 판매 타입(경매/일반)',
    register_date DATETIME NOT NULL COMMENT '작품 등록 날짜 (yyyy-MM-dd HH:mm:ss)',
    upload_name VARCHAR(100) NOT NULL COMMENT '업로드 작품명',
    storage_name  VARCHAR(40) NOT NULL UNIQUE COMMENT '서버에 저장된 작품명(UUID)',

    PRIMARY KEY (art_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists hashtag(
    hashtag_id BIGINT AUTO_INCREMENT COMMENT '해시태그_id',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '해시태그 이름(UNIQUE)',

    PRIMARY KEY (hashtag_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE if not exists art_hashtag(
    id BIGINT AUTO_INCREMENT,
    art_id BIGINT NOT NULL COMMENT '작품_id',
    hashtag_id BIGINT NOT NULL COMMENT '해시태그_ID',

    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE if not exists hashtag_list(
    art_id BIGINT,
    hashtag VARCHAR(100),

    PRIMARY KEY (art_id, hashtag)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists auction(
    auction_id BIGINT AUTO_INCREMENT COMMENT '경매_id',
    art_id BIGINT NOT NULL UNIQUE COMMENT '경매 대상 작품_id',
    user_id BIGINT COMMENT '해당 작품 경매에 참여한 user_id (최고 bid user_id - update query)',
    bid_price INT NOT NULL COMMENT '경매 가격 (최고 bid price - update query)',
    start_date DATETIME NOT NULL COMMENT '경매 시작 시각 (yyyy-MM-dd HH:mm:ss)',
    end_date DATETIME NOT NULL COMMENT '경매 종료 시각 (yyyy-MM-dd HH:mm:ss)',

    PRIMARY KEY (auction_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists auction_history(
    id BIGINT AUTO_INCREMENT,
    auction_id BIGINT NOT NULL COMMENT '경매_id',
    user_id BIGINT NOT NULL COMMENT '회원_id',
    art_id BIGINT NOT NULL COMMENT '작품_id',
    bid_price INT NOT NULL COMMENT '비드 금액',
    bid_date DATETIME NOT NULL COMMENT '비드 시각',

    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists purchase_history(
    purchase_id BIGINT AUTO_INCREMENT COMMENT '구매 내역_id',
    user_id BIGINT NOT NULL COMMENT '구매한 고객_id',
    art_id BIGINT NOT NULL UNIQUE COMMENT '구매한 작품_id',
    auction_id BIGINT UNIQUE COMMENT '경매를 통한 구매는 insert, 일반 구매는 null',
    price INT NOT NULL COMMENT '구매 가격',
    purchase_date DATETIME NOT NULL COMMENT '구매 날짜 (yyyy-MM-dd HH:mm:ss)',
    purchase_category VARCHAR(8) NOT NULL DEFAULT 'GENERAL' COMMENT '구매 카테고리 (경매 = AUCTION/일반 = GENERAL)',

    PRIMARY KEY (purchase_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE if not exists like_art(
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '작품을 찜한 user_id',
    art_id BIGINT NOT NULL COMMENT '찜된 작품_id',

     PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE if not exists point_history(
    point_id BIGINT AUTO_INCREMENT COMMENT '포인트 내역_id',
    user_id BIGINT NOT NULL COMMENT '이 포인트 내역의 주인 (user_id)',
    deal_type VARCHAR(8) default 'JOIN' COMMENT '포인트 활용 내역 enum(신규가입 = JOIN/충전 = CHARGE/환불 = REFUND/사용 = USE)',
    deal_amount INT default 0 COMMENT '충전/환불/사용한 포인트 양',
    point INT default 0 COMMENT '현재 user가 보유하고 있는 포인트 양',
    deal_date DATETIME NOT NULL COMMENT '거래된 날짜 yyyy-MM-dd HH:mm:ss',

    PRIMARY KEY (point_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- FK 제약조건 & UNIQUE 제약조건(복합 UNIQUE)
ALTER TABLE art
ADD CONSTRAINT art_ibfk1_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id);

ALTER TABLE art_hashtag
ADD CONSTRAINT art_hashtag_ibfk1_art_id
FOREIGN KEY (art_id)
REFERENCES art(art_id);

ALTER TABLE art_hashtag
ADD CONSTRAINT art_hashtag_ibfk2_hashtag_id
FOREIGN KEY (hashtag_id)
REFERENCES hashtag(hashtag_id);

ALTER TABLE art_hashtag
ADD UNIQUE(art_id, hashtag_id);

ALTER TABLE auction
ADD CONSTRAINT auction_ibfk1_art_id
FOREIGN KEY (art_id)
REFERENCES art(art_id);

ALTER TABLE auction
ADD CONSTRAINT auction_ibfk2_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id);

ALTER TABLE auction_history
ADD CONSTRAINT auction_history_ibfk1_auction_id
FOREIGN KEY (auction_id)
REFERENCES auction(auction_id);

ALTER TABLE auction_history
ADD CONSTRAINT auction_history_ibfk2_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id);

ALTER TABLE auction_history
ADD CONSTRAINT auction_history_ibfk3_art_id
FOREIGN KEY (art_id)
REFERENCES art(art_id);

ALTER TABLE purchase_history
ADD CONSTRAINT purchase_history_ibfk1_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id);

ALTER TABLE purchase_history
ADD CONSTRAINT purchase_history_ibfk2_art_id
FOREIGN KEY (art_id)
REFERENCES art(art_id);

ALTER TABLE purchase_history
ADD CONSTRAINT purchase_history_ibfk3_auction_id
FOREIGN KEY (auction_id)
REFERENCES auction(auction_id);

ALTER TABLE like_art
ADD CONSTRAINT like_art_ibfk1_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id);

ALTER TABLE like_art
ADD CONSTRAINT like_art_ibfk2_art_id
FOREIGN KEY (art_id)
REFERENCES art(art_id);

ALTER TABLE like_art
ADD UNIQUE(user_id, art_id);

ALTER TABLE point_history
ADD CONSTRAINT point_history_ibfk1_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id);