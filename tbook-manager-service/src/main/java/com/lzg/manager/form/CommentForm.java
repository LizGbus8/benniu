package com.lzg.manager.form;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 作者：LizG on 2018/10/7 18:01
 * 描述：
 */
@Data
public class CommentForm {
    @NotNull
    private String orderId;
    @NotNull
    private String content;
    @NotNull
    private String type;
}
