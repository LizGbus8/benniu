package com.lzg.manager.dao;

import com.lzg.manager.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/9/27 10:09
 * 描述：
 */
public interface SchoolDao extends JpaRepository<School,String> {
    School findBySchoolname(String schoolname);
}
