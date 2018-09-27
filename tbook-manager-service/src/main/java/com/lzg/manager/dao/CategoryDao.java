package com.lzg.manager.dao;

import com.lzg.manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/8/19 23:30
 * 描述：
 */
public interface CategoryDao extends JpaRepository<Category,Integer> {
    Category findByCategoryType(Integer categoryId);
    Category findByCategoryName(String categoryName);
}
