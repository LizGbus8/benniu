package com.lzg.product.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：LizG on 2018/7/27 22:36
 * 描述：商品
 */
@Data
@Entity
@DynamicUpdate
public class ProductInfo {

    /** 商品id */
    @Id
    private String productId;

    /** 商品名称 */
    private String productTitle;

    /** 商品描述 */
    private String productDesc;

    /** 商品图片1 */
    private String image1;

    /** 商品图片2 */
    private String image2;

    /** 商品图片3 */
    private String image3;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 类目编号 */
    private Integer categoryType;

    /** 订单生成时间 */
    private Date createTime;

    /** 订单更新时间*/
    private Date updateTime;

}
