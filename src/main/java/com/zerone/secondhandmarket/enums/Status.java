package com.zerone.secondhandmarket.enums;

/**
 * 前缀
 * Item Module:30
 * Login Module:10
 * Order Module:40
 * Cart Module:60
 * User Module:50
 * Upload Module:20
 * Notification Module:70
*/

public enum Status {
    ERROR(0),

    OK(10200),
    NOT_FOUND(10400),
    PASSWORD_WRONG(10401),
    HAS_REGISTERED(10402),

    UPLOAD_SUCCESSFUL(20200),

    ITEM_OK(30200),
    ITEM_INFO_GOT(30201),
    ITEM_LIST_GOT(30202),
    NO_SUCH_ITEM(0),
    NO_QUALIFIED_ITEMS(0),
    ITEM_ERROR(30400),
    INVALID_ITEM(0),
    DELETE_ITEM_ERROR(0),

    ORDER_OK(40200),
    NO_QUALIFIED_ORDERS(40400),
    INVALID_ORDER(40401),
    GENERATE_ORDER_ERROR(40402),
    ORDER_NOT_UNIQUE(40403),
    ORDER_ERROR(40404),

    USER_OK(50200),
    TOKEN_MISMATCH(50400),
    PASSWORD_ERROR(50401),
    USER_ERROR(50402),

    CART_OK(60200),
    CART_ERROR(60400),

    ;

    private final Integer code;

    Status(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
