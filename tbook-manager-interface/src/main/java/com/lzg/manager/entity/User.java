package com.lzg.manager.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 作者：LizG on 2018/7/28 20:23
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
public class User {

    /** 用户id */
    @Id
    private String userId;

    /** 用户昵称 */
    private String nickName;

    /** 用户昵称 */
    private String password;

    /** 用户头像 */
    private String photo;

    /** 学校 */
    private String school;


}
