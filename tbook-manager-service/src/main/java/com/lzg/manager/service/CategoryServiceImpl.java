package com.lzg.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.manager.dao.CategoryDao;
import com.lzg.manager.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 作者：LizG on 2018/8/19 23:36
 * 描述：
 */
@Service
@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category findCategoryByType(Integer type) {
        return categoryDao.findByCategoryType(type);
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryDao.findOne(id);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryDao.findByCategoryName(name);
    }

    @Override
    public Category add(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public Category delete(Category category) {
        category.setCategoryStatus(0);
        return update(category);
    }

    @Override
    public Category update(Category category) {
        return categoryDao.save(category);
    }
}
