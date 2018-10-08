package com.lzg.tbook.order.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 作者：LizG on 2018/8/19 23:02
 * 描述：
 */
@Data
public class WantForm {

    @NotNull
    private String userId;

    @NotNull
    private String contentId;
}
