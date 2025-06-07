package com.example.demo.common.constants;

public class CommonConstants {

    public static final int PAGE_SIZE = 10;

    // dateformat
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_MILLISECONDS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // request attribute 에 저장되는 인증 사용자 정보
    public static final String COMMON_REQUEST_PRINCIPAL_DETAILS = "principalDetails";

    // 사용자 인증 실패
    public static final String USER_NOT_FOUND = "사용자 정보가 없습니다.";

    // 사용자 검증 실패
    public static final String MEMBER_ERROR_PASSWORD = "자격 증명에 실패하였습니다.";

}
