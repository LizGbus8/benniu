package com.lzg.manager.dao;


import com.lzg.manager.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/7/28 17:47
 * 描述：
 */
public interface ContentDao extends JpaRepository<Content, String> {

    Page<Content> findByCategoryIdOrderByCreateTime(Integer categoryId, Pageable pageable);

    Page<Content> findByUserIdOrderByCreateTime(String userId, Pageable pageable);
}
