package com.lzg.sso.dao;

import com.lzg.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/9/21 22:36
 * 描述：
 */
public interface UserDao extends JpaRepository<User, String> {
    User findByOpenid(String openid);
}
