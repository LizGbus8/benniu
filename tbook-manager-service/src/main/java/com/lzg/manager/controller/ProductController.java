package com.lzg.manager.controller;

import com.lzg.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：LizG on 2018/8/3 22:58
 * 描述：
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/buy/{id}")
    public String buy(@PathVariable("id") String productId) {
        productService.buy(productId);
        return productService.queryMap(productId);
    }

    @GetMapping("/query/{id}")
    public String query(@PathVariable("id") String productId) {
        return productService.queryMap(productId);
    }
}
