package com.lzg.tbook.search.service;

import com.lzg.common.VO.ResultVO;


/**
 * 作者：LizG on 2018/10/9 14:07
 * 描述：
 */
public interface SearchItemService {

    /** 导入索引 */
    ResultVO importItems2Index() throws Exception;
}
