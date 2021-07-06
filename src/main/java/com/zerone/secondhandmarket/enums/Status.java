package com.zerone.secondhandmarket.enums;

/*
Item Module:
Login Module:
Order Module:
Cart Module:
User Module:
*/

public enum Status {
    ERROR(0),

    OK(10200),
    NOT_FOUND(10400),
    PASSWORD_WRONG(10401),
    HAS_REGISTERED(10402),

    ITEM_INFO_GOT(0),
    ITEM_LIST_GOT(0),
    NO_SUCH_ITEM(0), //
    NO_QUALIFIED_ITEMS(0),
    RELEASE_ITEM_ERROR(0),
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
