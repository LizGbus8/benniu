package com.lzg.manager.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LizG on 2018/9/27 10:06
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Tbcomment implements Serializable {

    @Id
    private String commentId;
    private String orderId;
    private String userId;
    private String fromNickname;
    private String type;
    private String content;

    private Date createTime;

    private Date updateTime;
}
