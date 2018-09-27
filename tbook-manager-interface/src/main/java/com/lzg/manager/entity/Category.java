package com.lzg.manager.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LizG on 2018/8/19 23:22
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Category implements Serializable {
    @Id
    @GeneratedValue
    private Integer categoryId;

    private String categoryName;

    private String categorySname;

    private Integer categoryType;

    private Integer categoryStatus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
