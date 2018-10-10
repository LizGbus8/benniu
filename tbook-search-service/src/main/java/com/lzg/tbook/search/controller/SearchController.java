package com.lzg.tbook.search.controller;

import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.tbook.search.entity.SearchItem;
import com.lzg.tbook.search.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 作者：LizG on 2018/10/9 13:58
 * 描述：
 */
@RestController
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @GetMapping("/search")
    public ResultVO search(@RequestParam("q") String queryString,
                           @RequestParam(value = "page",defaultValue = "1") Integer page,
                           @RequestParam("size") Integer size) throws Exception {

        if (StringUtils.isEmpty(queryString)){
            throw new TBookException(ResultEnum.PARAM_ERROT);
        }

        List<SearchItem> search = searchService.search(queryString, page, size);

        return ResultVOUtil.success(search);
    }
}
