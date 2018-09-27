package com.lzg.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.manager.dao.SchoolDao;
import com.lzg.manager.entity.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 作者：LizG on 2018/9/27 10:14
 * 描述：
 */
@Slf4j
@Service
@Component
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolDao schoolDao;

    @Override
    public School findSchoolByName(String schoolname) {
        return schoolDao.findBySchoolname(schoolname);
    }
}
