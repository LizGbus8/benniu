package com.lzg.manager.controller;

import com.lzg.common.VO.ResultVO;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.dto.ProductDTO;
import com.lzg.manager.entity.Category;
import com.lzg.manager.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者：LizG on 2018/8/3 22:58
 * 描述：
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/by_category")
    public ResultVO getProductsByCgr(@RequestParam(value = "id") Integer categoryId,
                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest request = new PageRequest(page, size);
        List<ProductDTO> productDTOS = productService.getProductsByCgr(categoryId, request);

        log.info("productDTOS is {}",productDTOS);
        return ResultVOUtil.success(productDTOS);
    }

    @GetMapping("/product")
    public ResultVO getProductsByUserId(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest request = new PageRequest(page, size);
        List<ProductDTO> productDTOS = productService.getProductsByUserId(userId,request);

        log.info("productDTOS is {}",productDTOS);
        return ResultVOUtil.success(productDTOS);
    }
}
