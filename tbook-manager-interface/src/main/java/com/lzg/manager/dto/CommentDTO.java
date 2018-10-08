package com.lzg.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LizG on 2018/10/8 16:50
 * 描述：
 */
@Data
public class CommentDTO implements Serializable {

    private String commentId;
    private String orderId;
    private String productTitle;
    private String userId;
    private String fromNickname;
    private String type;
    private String content;

    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date updateTime;
}
