package com.lzg.common.exception;

import com.lzg.common.enums.ResultEnum;

/**
 * 作者：LizG on 2018/7/23 14:56
 * 描述：自定义异常
 */
public class TBookException extends RuntimeException {
    private Integer code;
    private String message;

    public TBookException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
    }

    public TBookException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
