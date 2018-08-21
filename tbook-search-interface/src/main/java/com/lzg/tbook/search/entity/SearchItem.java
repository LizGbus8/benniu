package com.lzg.tbook.search.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 作者：LizG on 2018/8/21 20:36
 * 描述：
 */
@Data
public class SearchItem implements Serializable {
    /** 内容id */
    private String contentId;

    /** 商品标题 */
    private String productTitle;

    /** 商品描述 */
    private String productDesc;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品图片*/
    private String productImg1;

    /** 内容类目名称 */
    private String categoryName;
}
