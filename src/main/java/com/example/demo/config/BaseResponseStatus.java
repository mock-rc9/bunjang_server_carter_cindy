package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2011, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2012, "이메일 형식을 확인해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2013, "비밀번호를 입력하세요."),
    POST_USERS_EMPTY_NAME(false, 2014, "이름을 정확히 입력하세요."),

    // [POST] /users/logIn
    POST_LOGIN_EMPTY_EMAIL(false, 2015, "아이디를 입력해주세요."),
    POST_LOGIN_INVALID_EMAIL(false, 2016, "아이디는 이메일주소 형식으로 입력해주세요."),
    POST_LOGIN_EMPTY_PASSWORD(false, 2017, "비밀번호를 입력해주세요."),


    // [POST] /app/goods

    POST_GOODS_LACK_CONTENT(false, 2020, "설명을 입력해주세요.(10자 이상)"),
    POST_GOODS_EMPTY_IMG(false, 2021, "이미지를 넣어주세요."),
    POST_GOODS_LACK_NAME(false, 2022, "제목을 입력해 주세요(2글자 이상)"),
    POST_GOODS_EMPTY_CATEGORY(false, 2023, "카테고리를 입력해 주세요."),
    POST_GOODS_EMPTY_PRICE(false, 2024, "가격을 입력해 주세요."),
    GOODS_EMPTY_GOODS_ID(false, 2025, "없는 상품입니다."),
    GOODS_EMPTY_USER_GOODS(false, 2026, "상품,유저를 확인해 주세요"),

    // [POST] /addresses
    POST_ADDRESS_EMPTY_NAME(false, 2027, "이름을 입력해주세요."),
    POST_ADDRESS_EMPTY_PHONENUM(false, 2028, "전화번호를 입력해주세요."),
    POST_ADDRESS_INVALID_PHONENUM(false, 2029, "유효하지 않은 전화번호입니다"),
    POST_ADDRESS_EMPTY_ADDRESS(false, 2030, "주소를 입력해주세요."),
    POST_ADDRESS_EMPTY_ADDRESSDETAIL(false, 2031, "상세주소를 입력해주세요."),

    // [POST] /orders
    POST_ORDER_EMPTY_ORDERPAYMENTMETHOD(false, 2032, "결제 수단을 입력해주세요."),


    //[POST] /payment
    PAYMENTS_EMPTY_PAYMENTS_ID (false, 2040, "결제 수단을 확인해 주세요."),
    PAYMENTS_EMPTY_USER_PAYMENT(false, 2041, "결제 수단,유저를 확인해 주세요."),

    //[PATCH] /mypages
    PATCH_USERINFO_EMPTY_USERNICKNAME(false, 2042, "상점명은 최소 2자, 최대 10자까지 입력 가능합니다."),
    PATCH_USERINFO_INVALID_USERNICKNAME(false, 2043, "상점명은 띄어쓰기 없이 한글, 영문, 숫자만 가능합니다."),
    REVIEW_EMPTY_BUYER_ID(false, 2050, "유효 하지 않는 리뷰 요청 입니다"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    INACTIVE_USER(false, 3015, "비활성화된 유저입니다."),
    DELETED_USER(false, 3016, "탈퇴한 유저입니다."),

    // [PATCH] /follows/:followings
    FAILED_TO_UNFOLLOW(false, 3017, "팔로우하지 않은 유저입니다."),




    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    MODIFY_FAIL_GOODS(false,4020,"상품 수정 실패"),


    DELETE_FAIL_GOODS(false,4021,"상품 삭제 실패"),



    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    //[PATCH] /addresses/{addressId}
    MODIFY_FAIL_ADDRESS(false, 4013, "주소 변경에 실패하였습니다."),

    //[DELETE] /addresses/{addressId}
    DELETE_FAIL_ADDRESS(false, 4014, "주소 정보 삭제에 실패하였습니다."),

    //[DELETE] /orders/{orderIdx}
    DELETE_FAIL_ORDER(false, 4015, "주문 취소에 실패하였습니다."),

    MODIFY_FAIL_PAYMENT(false,4022,"계좌 수정 실패"),

    DELETE_FAIL_PAYMENT(false,4023,"계좌 삭제 실패"),


    //[PATCH] /mypages
    MODIFY_FAIL_USERINFO(false, 4016, "상점 소개 변경에 실패하였습니다."),

    //[PATCH] /follows/{followingIdx}
    MODIFY_FAIL_UNFOLLOW(false, 4017, "팔로우 취소에 실패하였습니다."),
    MODIFY_FAIL_FOLLOW(false, 4018, "팔로우에 실패하였습니다."),

    MODIFY_FAIL_REVIEW(false,4030,"리뷰 수정 실패"),
    REVIEW_EMPTY_REVIEW_ID(false,4031,"잘못된 리뷰 연결"),
    DELETE_FAIL_REVIEW(false,4032,"리뷰 삭제 실패"),

    DELETE_FAIL_USER(false, 4033, "유저 탈퇴에 실패하였습니다."),

    DELETE_FAIL_STORELIKE(false, 4034, "찜 삭제에 실패하였습니다.");



    // 5000
    // 6000


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}