package com.lzg.common.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 作者：LizG on 2018/7/22 9:10
 * 描述：商品详情VO
 */
@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrcie;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String icon;
}
