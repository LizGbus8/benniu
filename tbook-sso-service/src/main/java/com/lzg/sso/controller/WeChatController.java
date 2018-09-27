package com.lzg.sso.controller;

import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.entity.User;
import com.lzg.sso.redis.RedisUtil;
import com.lzg.sso.service.impl.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LizG on 2018/9/24 1:20
 * 描述：
 */
@RequestMapping(value = "/token")
@RestController
@Slf4j
public class WeChatController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "/user")
    public Map wxLogin(HttpServletRequest request, HttpSession session){

        //获取用户登录传过来的code
        String code = request.getParameter("code");
        log.info("code is {}", code);

        Map map = new HashMap();

        // 登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        map = userInfoService.wxlogin(code);
        return map;
    }

    @GetMapping(value = "/user")
    public ResultVO getUserInfo(@RequestParam String token){


        // 登录凭证不能为空
        if (token == null || token.length() == 0) {
            throw new TBookException(ResultEnum.USER_AND_PWD_ERROR);
        }

        User findUser = userInfoService.getUserInfo(token);
        if (findUser == null) {
            throw new TBookException(ResultEnum.NOT_HAS_LOGIN);
        }
        if (findUser.getNickname() == null || findUser.getNickname().isEmpty()) {
            return ResultVOUtil.custom(775,"未注册");
        } else {
            return ResultVOUtil.success();
        }
    }

    @PostMapping(value = "/verify")
    public Map verify(HttpServletRequest request, HttpSession session){

        String token = request.getParameter("token");
        log.info("token is {}", token);

        Object findToken = redisUtil.get(token);

        log.info("findtoken is {}", findToken);
        Map map = new HashMap();
        if (findToken != null) {
            map.put("isValid", true);
        } else {
            map.put("isValid", false);
        }

        return map;
    }
}
