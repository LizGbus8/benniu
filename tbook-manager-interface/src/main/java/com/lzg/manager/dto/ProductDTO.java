package com.lzg.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 作者：LizG on 2018/7/28 0:20
 * 描述：
 */
@Data
public class ProductDTO {
    /** 商品id */
    private String productId;

    /** 商品title */
    private String productTitle;

    /** 商品描述 */
    private String productDesc;

    private String productImg1;

    /** 内容 */
    private Integer contentStatus;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 创建时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;

}
