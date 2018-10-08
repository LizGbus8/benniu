package com.lzg.tbook.content.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 作者：LizG on 2018/9/27 13:53
 * 描述：
 */
@Data
@AllArgsConstructor
public class HotContentDTO implements Serializable {

    private String categorySname;

    private HotProductDTO hotProductDTO;

    private Double hot;
}
