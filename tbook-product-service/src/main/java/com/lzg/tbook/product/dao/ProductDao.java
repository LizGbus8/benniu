package com.lzg.tbook.product.dao;

import com.lzg.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/7/28 17:47
 * 描述：
 */
public interface ProductDao extends JpaRepository<ProductInfo,String> {

}
