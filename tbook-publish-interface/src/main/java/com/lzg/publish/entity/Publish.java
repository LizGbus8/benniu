package com.lzg.publish.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 作者：LizG on 2018/7/27 22:55
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
public class Publish {

    /** 发布id */
    @Id
    private String publishId;

    /** 发布人id */
    private String publisherId;

    /** 发布状态  发布中-0 下架-1 默认-0*/
    private Integer publishStatus;

    /** 商品id */
    private String productId;

    /** 点赞数 */
    private Integer starNum;

    /** 订单生成时间 */
    private Date createTime;

    /** 订单更新时间*/
    private Date updateTime;
}
