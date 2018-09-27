package com.lzg.manager.service;

import com.lzg.manager.entity.School;

/**
 * 作者：LizG on 2018/9/27 10:13
 * 描述：
 */
public interface SchoolService {
    School findSchoolByName(String schoolname);
}
