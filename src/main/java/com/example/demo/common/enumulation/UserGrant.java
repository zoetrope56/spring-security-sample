package com.example.demo.common.enumulation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserGrant {

    // 권한
    ADMIN("ADMIN", "관리자"),
    USER("USER", "사용자");

    private final String code;
    private final String desc;

}
