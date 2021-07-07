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

    RELEASE_ITEM_SUCCESS(30200),
    ITEM_INFO_GOT(0),
    ITEM_LIST_GOT(0),
    NO_SUCH_ITEM(0),
    NO_QUALIFIED_ITEMS(0),
    RELEASE_ITEM_ERROR(30400),
    INVALID_ITEM(0),
    DELETE_ITEM_ERROR(0),

    NO_QUALIFIED_ORDERS(0),
    INVALID_ORDER(0),
    GENERATE_ORDER_ERROR(0),

    ;

    private final Integer code;

    Status(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
