package com.example.demo.common.enumulation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserState {

    // 인증
    USER_ALIVE("0000", "정상적인 사용자입니다."),
    USER_NOT_FOUND( "1000", "사용자 정보가 없습니다."),
    USER_STATE_STOP("1001", "사용중지 계정입니다."),
    USER_STATE_RETRY("1002", "비밀번호 오류 중지 계정입니다.")
    ;

    private String code;
    private String desc;

    UserState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
