package com.lzg.tbook.content.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：LizG on 2018/9/30 10:08
 * 描述：
 */
@Data
public class ContentDTO implements Serializable {


    private String contentId;

    /** 发布者id */
    private String userId;

    /** 发布者名字 */
    private String username;

    /** 发布者头像 */
    private String image;

    /** 分类id*/
    private Integer categoryId;

    /** 点赞数 */
    private Integer contentStar;

    /** 商品title */
    private String productTitle;

    /** 商品描述 */
    private String productDesc;

    private String productImg1;
    private String productImg2;
    private String productImg3;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 浏览数 */
    private Integer content_read;

    /** 创建时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;

    /** 更新时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date updateTime;
}
