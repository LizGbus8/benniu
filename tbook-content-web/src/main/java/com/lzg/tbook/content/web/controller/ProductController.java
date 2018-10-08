package com.lzg.tbook.content.web.controller;

import com.lzg.common.VO.ResultVO;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.tbook.content.web.dto.ContentDTO;
import com.lzg.tbook.content.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：LizG on 2018/10/2 23:12
 * 描述：
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/product/{id}")
    public ResultVO getDetailInfo(@PathVariable("id") String id){
        ContentDTO content = productService.findContent(id);
        log.info("content is {}",content);
        return ResultVOUtil.success(content);
    }


}
