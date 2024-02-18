package com.payhere.phtest.common.enumulation;

public enum ProductSize {
    SMALL("small", "스몰 사이즈"), LARGE("large", "라지 사이즈");

    private String value;
    private String desc;


    ProductSize(String v, String d) {
        value = v;
        desc = d;
    }

    public String value() {
        return value;
    }

    public static ProductSize fromValue(String v) {
        for (ProductSize c : ProductSize.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
