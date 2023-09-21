package com.example.naitei19javaecommerce.constant;

public enum Status {
    ORDER_PLACED(1, "Order placed"),
    ORDER_CONFIRMED(2, "Order confirmed"),
    ON_THE_WAY(3, "On the way"),
    ORDER_RECEIVED(4, "Order received"),
    ORDER_REJECTED(5, "Order Rejected");

    private final Integer code;
    private final String description;

    Status(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
