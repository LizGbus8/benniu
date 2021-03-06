package com.lzg.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 作者：LizG on 2018/9/21 22:11
 * 描述：
 */
@Data
public class UserDTO {

    /** 用户id */
    @Id
    private String userId;

    /** 用户昵称 */
    private String username;

    /** 用户昵称 */
    private Integer sex;

    /** 用户头像 */
    private String photo;

    /** 学校代码 */
    private String schoolcode;

    /** 兴趣 */
    private Integer interest;

    /** 创建时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;

    /** 更新时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date updateTime;
}
