package com.lzg.manager.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：LizG on 2018/8/9 0:26
 * 描述：内容
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Content implements Serializable {
    /** 内容id */
    @Id
    private String contentId;

    /** 用户id */
    private String userId;

    /** 分类id*/
    private Integer categoryId;

    /** 内容 */
    private Integer contentStatus;

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

    /** 联系方式 */
    private String contact;

    /** 浏览数 */
    private Integer content_read;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
