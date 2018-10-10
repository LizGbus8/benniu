package com.lzg.manager.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：LizG on 2018/10/9 22:00
 * 描述：
 */
@Data
public class Message implements Serializable {
    private String id;
    private Object data;
}
