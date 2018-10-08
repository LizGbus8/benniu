package com.lzg.tbook.content.web.aop;

import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：LizG on 2018/9/27 13:56
 * 描述：
 */
@Aspect
@Component
@Slf4j
public class UserAspect {
    @Autowired
    RedisUtil redisUtil;

    @Pointcut("execution(public * com.lzg.tbook.content.web.controller.*Controller.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() {
        log.info("doVerify()");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (token == null) {
            log.warn("【登录校验】Ruquest中查不到token");
            throw new TBookException(ResultEnum.VERIFY_ERROR);
        }

        Object user = redisUtil.get(token);
        if (user == null) {
            log.warn("【登录校验】Redis中查不到token");
            throw new TBookException(ResultEnum.VERIFY_ERROR);
        }
    }

}
