package com.lzg.sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.InterestUtils;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.dto.UserDTO;
import com.lzg.manager.entity.School;
import com.lzg.manager.entity.User;
import com.lzg.manager.service.SchoolService;
import com.lzg.sso.form.UserInfoForm;
import com.lzg.sso.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @Reference
    private SchoolService schoolService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "/user")
    public Map wxLogin(HttpServletRequest request, HttpSession session) {

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
    public ResultVO getUserInfo(@RequestParam String token) {


        // 登录凭证不能为空
        if (token == null || token.length() == 0) {
            throw new TBookException(ResultEnum.VERIFY_ERROR);
        }

        User findUser = userInfoService.getUserInfo(token);
        if (findUser == null) {
            throw new TBookException(ResultEnum.NOT_HAS_LOGIN);
        }
        if (findUser.getNickname() == null || findUser.getNickname().isEmpty()) {
            return ResultVOUtil.custom(775, "未注册");
        } else {
            return ResultVOUtil.success();
        }
    }

    @PostMapping(value = "/verify")
    public Map verify(HttpServletRequest request, HttpSession session) {

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

    @PostMapping(value = "/user/register")
    public ResultVO register(@Valid @RequestBody UserInfoForm userInfoForm, BindingResult bindingResult) {

        /* 验证表单 */
        log.info("userInfoForm is {}", userInfoForm);
        if (bindingResult.hasErrors()) {
            throw new TBookException(ResultEnum.FORM_PARAM_ERROR);
        }

        /* 获取token */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        /* 获取用户信息 */
        String json = (String) redisUtil.get(token);
        if (json == null){
            throw new TBookException(ResultEnum.TOKEN_ERROT);
        }
        User user = JsonUtilByGson.jsonToBean(json, User.class);


        //查找学校
        School school = schoolService.findSchoolByName(userInfoForm.getSchoolname());
        if (school == null) {
            throw new TBookException(ResultEnum.SCHOOL_NOT_EXIST);
        }
        user.setSchoolcode(school.getSchoolcode());

        //兴趣convert
        Integer interest = InterestUtils.convert2Interest(userInfoForm.getInterest());
        user.setInterest(interest);

        //联系方式
        String contact = userInfoForm.getContact_type() + "-" + userInfoForm.getContact_value();
        user.setContact(contact);

        BeanUtils.copyProperties(userInfoForm,user);

        User register = userInfoService.register(token,user);

        return ResultVOUtil.success(register);
    }

    @GetMapping(value = "/userinfo")
    public ResultVO getUserInfo() {
        /* 获取token */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        log.info("token is {}",token);

        /* 获取用户信息 */
        String json = (String) redisUtil.get(token);
        if (json == null){
            throw new TBookException(ResultEnum.NOT_HAS_LOGIN);
        }
        User user = JsonUtilByGson.jsonToBean(json, User.class);

        log.info("user is {}",user);
        return ResultVOUtil.success(user);
    }

}
