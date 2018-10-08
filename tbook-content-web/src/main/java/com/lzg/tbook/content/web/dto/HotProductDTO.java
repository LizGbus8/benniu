package com.lzg.tbook.content.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：LizG on 2018/9/30 9:47
 * 描述：
 */
@Data
public class HotProductDTO implements Serializable {

    private String contentId;

    private String productImg1;

    private String productTitle;

    private BigDecimal productPrice;

    /** 创建时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;
}
