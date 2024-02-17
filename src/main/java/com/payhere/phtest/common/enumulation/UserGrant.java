package com.payhere.phtest.common.enumulation;

public enum UserGrant {

    ADMIN("ADMIN", "관리자"),
    USER("USER", "사용자");

    private String value;
    private String desc;

    UserGrant(String v, String d) {
        value = v;
        desc = d;
    }

    public String value() {
        return value;
    }

    public static UserGrant fromValue(String v) {
        for (UserGrant c : UserGrant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
