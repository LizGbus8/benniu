package com.lzg.common.VO;

import lombok.Data;

/**
 * 作者：LizG on 2018/7/22 9:01
 * 描述：
 */
@Data
public class ResultVO<T> {

    /**错误码 */
    private Integer code;

    /**提示信息 */
    private String msg = "";

    /**相应具体内容 */
    private T data;
}
