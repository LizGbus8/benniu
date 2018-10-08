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
 * 作者：LizG on 2018/9/27 10:06
 * 描述：
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class School implements Serializable {

    @Id
    private Integer Id;

    private String schoolid;

    private String schoolcode;

    private String schoolname;

    private String province;
}
