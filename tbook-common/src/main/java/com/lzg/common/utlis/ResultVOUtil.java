package com.lzg.common.utlis;

import com.lzg.common.VO.ResultVO;

/**
 * 作者：LizG on 2018/7/22 16:57
 * 描述：
 */
public class ResultVOUtil {

    /**
     * 描述: 成功
     *
     * @return
     * @Param
     */
    public static ResultVO success(Object data) {
        ResultVO<Object> resultVO = new ResultVO<Object>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 描述: 成功（没有data）
     * @Param
     * @return
     */
    public static ResultVO success() {
        ResultVO<Object> resultVO = new ResultVO<Object>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
}
