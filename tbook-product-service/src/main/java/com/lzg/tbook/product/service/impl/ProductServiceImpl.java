package com.lzg.tbook.product.service.impl;

import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.tbook.product.dao.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：LizG on 2018/7/28 14:23
 * 描述：
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductInfo findProductById(String productId) {
        return productDao.findOne(productId);
    }

    @Override
    public ProductInfo saveProduct(ProductInfo productInfo) {
        return productDao.save(productInfo);
    }

    @Override
    public ProductInfo updateProduct(ProductInfo productInfo) {
        return productDao.save(productInfo);
    }

    @Override
    public void increaseStock(String productId) {
        //判断是否存在该商品
        ProductInfo productInfo = productDao.findOne(productId);
        if (productInfo == null) {
            log.error("【商品加库存】加库存失败, productId={}", productId);
            throw new TBookException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        //增加库存
        int result = productInfo.getProductStock() + 1;
        if (result < 0) {
            log.error("【商品加库存】加库存失败, result={}", result);
            throw new TBookException(ResultEnum.PRODUCT_STOCK_ERROR);
        }

        //更新
        productInfo.setProductStock(result);
        productDao.save(productInfo);
    }

    @Override
    public void decreaseStock(String productId) {
        //判断是否存在该商品
        ProductInfo productInfo = productDao.findOne(productId);
        if (productInfo == null) {
            log.error("【商品减库存】减库存失败, productId={}", productId);
            throw new TBookException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        //增加库存
        int result = productInfo.getProductStock() - 1;
        if (result < 0) {
            log.error("【商品减库存】减库存失败, result={}", result);
            throw new TBookException(ResultEnum.PRODUCT_STOCK_ERROR);
        }

        //更新
        productInfo.setProductStock(result);
        productDao.save(productInfo);
    }
}
