//package com.lzg.sso.service.impl;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.lzg.common.enums.ResultEnum;
//import com.lzg.common.exception.TBookException;
//import com.lzg.common.utlis.JsonUtil;
//import com.lzg.common.utlis.JsonUtilByGson;
//import com.lzg.common.utlis.KeyUtil;
//import com.lzg.manager.dto.UserDTO;
//import com.lzg.manager.entity.User;
//import com.lzg.sso.dao.UserDao;
//import com.lzg.sso.redis.RedisUtil;
//import com.lzg.sso.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
///**
// * 作者：LizG on 2018/9/21 22:23
// * 描述：
// */
//@Slf4j
//@Service
//@Component
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    private Integer SESSION_EXPIRE = 30000;
//
//    private static final String TOKEN = "token-";
//
//    @Override
//    public UserDTO register(User user) {
//        return null;
//    }
//
//    @Override
//    public String login(String username, String password) {
//        //1.判断用户名与密码是否正确
//        User findUser = userDao.findByUsernameAndPassword(username, password);
//
//        if (findUser == null) {
//            throw new TBookException(ResultEnum.USER_AND_PWD_ERROR);
//        }
//
//        //2.生成token
//        String token = KeyUtil.getToken();
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(findUser, userDTO);
//        redisUtil.set(TOKEN + token, JsonUtilByGson.beanToJson(userDTO), SESSION_EXPIRE);
//
//        return token;
//    }
//
//    @Override
//    public UserDTO getUserByToken(String token) {
//        String userJson = (String) redisUtil.hget(TOKEN, token);
//        if (userJson == null || StringUtils.isEmpty(userJson)) {
//            throw new TBookException(ResultEnum.USER_AND_PWD_ERROR);
//        }
//        redisUtil.expire(TOKEN + token, SESSION_EXPIRE);
//        UserDTO userDTO = JsonUtilByGson.jsonToBean(userJson, UserDTO.class);
//        return userDTO;
//    }
//}
