package com.lzg.manager.service;

import com.lzg.manager.entity.Category;

import java.util.List;

/**
 * 作者：LizG on 2018/8/19 23:31
 * 描述：
 */
public interface CategoryService {

    /** find*/
    Category findCategoryByType(Integer type);

    Category findCategoryById(Integer id);

    Category findCategoryByName(String name);

    Category add(Category category);

    Category delete(Category category);

    Category update(Category category);

    List<Category> findAll();
}
