package com.lzg.sso.service;

import com.lzg.manager.entity.User;

import java.util.Map;

/**
 * 作者：LizG on 2018/10/4 9:11
 * 描述：
 */
public interface UserInfoService {
    
    User getWxUserInfo(String accessToken, String openId) throws Exception;

    Map wxlogin(String code);

    String getOpenId(String code);

    String getAccessToken();

    User getUserInfo(String token);

    User getUserInfoByOpenid(String openid);

    User register(String token, User user);
}
