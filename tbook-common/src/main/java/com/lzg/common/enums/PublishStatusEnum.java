package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/29 17:39
 * 描述：
 */
@Getter
public enum PublishStatusEnum {

    PUBLISHED(0,"发布中"),
    DOWN(1,"下架")
    ;
    private Integer code;

    private String message;

    PublishStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
