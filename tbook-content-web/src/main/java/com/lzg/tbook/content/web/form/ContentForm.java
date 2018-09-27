package com.lzg.tbook.content.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 作者：LizG on 2018/8/19 23:02
 * 描述：
 */
@Data
public class ContentForm {

    private String openid;

    @NotNull
    private String categoryName;

    @NotNull
    private String productTitle;

    @NotNull
    private String productDesc;

    @NotNull
    private String productImg1;

    private String productImg2;

    private String productImg3;

    @NotNull
    private BigDecimal productPrice;

    @NotNull
    private Integer productStock;

    @NotNull
    private String contactType;

    @NotNull
    private String contactNum;

    @NotNull
    private String schoolName;

}
