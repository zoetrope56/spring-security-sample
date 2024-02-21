package com.example.demo.common.enumulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserGrant implements CodeGroup {

    ADMIN("ADMIN", "관리자"),
    USER("USER", "사용자");

    private final String code;
    private final String desc;

    @Override
    public String getGroupName() {
        return null;
    }

}
