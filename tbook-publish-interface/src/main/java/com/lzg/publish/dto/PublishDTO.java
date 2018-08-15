package com.lzg.publish.dto;

import lombok.Data;

import java.util.Date;

/**
 * 作者：LizG on 2018/7/28 0:31
 * 描述：发布数据传输对象
 */
@Data
public class PublishDTO {

    /** 发布id */
    private String publishId;

    /** 发布人id */
    private String publisherId;

    /** 发布状态  发布中-0 下架-1 默认-0*/
    private Integer publishStatus;

    /** 商品id */
    private ProductInfo productInfo;

    /** 点赞数 */
    private Integer starNum;

    /** 订单生成时间 */
    private Date createTime;

    /** 订单更新时间*/
    private Date updateTime;
}
