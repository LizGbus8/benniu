package com.lzg.order.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 作者：LizG on 2018/8/9 0:36
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Orders {

    /** 订单id */
    @Id
    private String orderId;

    /** 用户id */
    private String userId;

    /** 订单类型 */
    private Integer orderType;

    /** 订单状态 交易中-0 关闭交易-1 完成交易-3 已评价-4*/
    private Integer orderStatus;

    /** 买家id */
    private String tradersId;

    /** 买家昵称*/
    private String tradersNickName;

    /** 联系方式*/
    private String tradersContact;

    /** 买家评价 */
    private String commented;

    /** 商品id */
    private String contentId;

    /** 订单生成时间 */
    private Date createTime;

    /** 订单更新时间*/
    private Date updateTime;

    /** 完结时间 */
    private Date finishTime;

    /** 评价时间 */
    private Date commentedTime;
}
