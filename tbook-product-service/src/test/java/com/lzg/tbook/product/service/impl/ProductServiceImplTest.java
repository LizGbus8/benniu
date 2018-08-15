package com.lzg.tbook.product.service.impl;

import com.lzg.product.entity.ProductInfo;
import com.lzg.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 作者：LizG on 2018/7/28 18:05
 * 描述：
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Test
    public void findProductById() {
        ProductInfo result = productService.findProductById("3306");
        log.info("插入商品信息 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void saveProduct() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("3306");
        productInfo.setProductTitle("火腿肠");
        productInfo.setProductDesc("金锣");
        productInfo.setProductStock(5);
        productInfo.setProductPrice(new BigDecimal(3.5));
        productInfo.setCategoryType(3);

        ProductInfo result = productService.saveProduct(productInfo);
        log.info("插入商品信息 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateProduct() {
        ProductInfo productInfo = productService.findProductById("3306");
        productInfo.setProductDesc("金锣plus");
        ProductInfo result = productService.updateProduct(productInfo);
        log.info("插入商品信息 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void increaseStock() {
        productService.increaseStock("3306");
    }

    @Test
    public void decreaseStock() {
        productService.decreaseStock("3306");
    }
}