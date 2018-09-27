package com.lzg.sso.service;

import com.lzg.manager.dto.UserDTO;
import com.lzg.manager.entity.User;

/**
 * 作者：LizG on 2018/9/21 22:04
 * 描述：
 */
public interface UserService {
    UserDTO register(User user);

    String login(String username, String password);

    UserDTO getUserByToken(String token);
}
