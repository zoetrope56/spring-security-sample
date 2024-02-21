package com.example.demo.common.enumulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSize implements CodeGroup {
    SMALL("small"), LARGE("large");

    private final String code;

    @Override
    public String getGroupName() {
        return null;
    }

}
