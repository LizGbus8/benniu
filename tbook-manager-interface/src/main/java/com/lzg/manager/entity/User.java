package com.lzg.manager.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LizG on 2018/7/28 20:23
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class User implements Serializable {

    /** 用户id */
    @Id
    private String openid;

    /** 用户昵称 */
    private String nickname;

    /** 用户昵称 */
    private Integer sex;

    /** 用户头像 */
    private String headImgUrl;

    /** 学校代码 */
    private String schoolcode;

    /** 兴趣 */
    private Integer interest;

    /** 联系方式 */
    private String contact;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
