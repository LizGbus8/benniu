package com.lzg.product.service;

import com.lzg.product.entity.ProductInfo;

/**
 * 作者：LizG on 2018/7/27 23:36
 * 描述：商品
 */
public interface ProductService {
    /**
     * 描述:通过id查找商品
     * @Param
     * @return
     */
    ProductInfo findProductById(String productId);

    /**
     * 描述: 保存商品
     * @Param
     * @return
     */
    ProductInfo saveProduct(ProductInfo productInfo);

    /**
     * 描述: 修改商品信息
     * @Param
     * @return
     */
    ProductInfo updateProduct(ProductInfo productInfo);

    /**
     * 描述: 加库存
     * @Param productId-商品id
     * @return
     */
    void increaseStock(String productId);

    /**
     * 描述: 减库存
     * @Param productId-商品id
     * @return
     */
    void decreaseStock(String productId);
}
