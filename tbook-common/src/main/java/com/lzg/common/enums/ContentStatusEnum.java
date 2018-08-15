package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/21 23:24
 * 描述：内容状态
 */
@Getter
public enum ContentStatusEnum {
    UP(0, "发布中"),
    DOWN(1, "下架");
    private Integer code;

    private String message;

    ContentStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
