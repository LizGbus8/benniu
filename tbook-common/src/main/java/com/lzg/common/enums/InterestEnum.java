package com.lzg.common.enums;

/**
 * 作者：LizG on 2018/9/27 12:08
 * 描述：
 */
public enum  InterestEnum {
    IT(0,"计算机"),
    EN(1,"成功支付")
    ;

    private Integer code;

    private String message;

    InterestEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
