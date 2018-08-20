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

    @NotNull
    private String userId;

    @NotNull
    private Integer categoryType;

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

}
