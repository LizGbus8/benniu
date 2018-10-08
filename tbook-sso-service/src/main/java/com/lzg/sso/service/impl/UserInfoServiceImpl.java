package com.lzg.sso.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.common.utlis.http.HttpRequest;
import com.lzg.manager.entity.User;
import com.lzg.sso.dao.UserDao;
import com.lzg.sso.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LizG on 2018/9/25 8:58
 * 描述：
 */
@Component
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserDao userDao;

    private static String APPID = "wx6adb56a19858f16f";

    private static String SECRET = "f93b5e748c6c5dabb884868d3eff6e89";

    // 网页授权获取code
    public final static String GetPageCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";

    // 网页授权接口
    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET;

    // 网页授权得到用户基本信息接口
    public final static String GetPageUsersUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    // 网页授权得到用户基本信息接口
    public final static String GetPageOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=CODE&grant_type=authorization_code";

    /**
     * 得到微信用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    @Override
    public User getWxUserInfo(String accessToken, String openId) throws Exception {

        User weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = GetPageUsersUrl;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
                "OPENID", openId);

        // 获取用户信息
        String st = HttpRequest
                .sendGet(requestUrl);

        JSONObject jsonObject = new JSONObject(st);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new User();
                // 用户的标识
                weixinUserInfo.setOpenid(jsonObject.getString("openid"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            }
        }
        return weixinUserInfo;
    }

    @Override
    public Map wxlogin(String code){

        //获取openid
        String openid = getOpenId(code);

        //获取token
        String accessToken = getAccessToken();

        //获取userinfo
        //User user = getUserInfo(accessToken,openid);//TODO 不支持api

        User user = new User();
        user.setOpenid(openid);

        //加入数据库
        User findUser = userDao.findByOpenid(openid);
        if (findUser == null){
            userDao.save(user);
            Map map = new HashMap();
            map.put("token", accessToken);
            return map;
        }

        //加入redis服务器
        redisUtil.set(accessToken, JsonUtilByGson.beanToJson(findUser), 7200);

        Map map = new HashMap();
        map.put("token", accessToken);
        return map;
    }

    @Override
    public String getOpenId(String code){

        log.info("appid : {}", APPID);
        log.info("secret : {}", SECRET);

        // 拼接请求地址
        String requestUrl = GetPageOpenIdUrl;
        requestUrl = requestUrl.replace("CODE", code);

        // 获取用户信息
        String st = null;
        try {
            st = HttpRequest
                    .sendGet(requestUrl);
        } catch (Exception e) {
            log.error("GetOpenid is fail");
            e.printStackTrace();
        }

        // 解析相应内容（转换成json对象）
        JSONObject json = null;
        String openid = null;

        try {
            json = new JSONObject(st);
            // 用户的唯一标识（openid）
            openid = (String) json.get("openid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        log.info("openid = {}", openid);

        return openid;
    }

    @Override
    public String getAccessToken(){

        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6adb56a19858f16f&secret=f93b5e748c6c5dabb884868d3eff6e89";

        // 获取用户信息
        String st = null;
        try {
            st = HttpRequest
                    .sendGet(requestUrl);
        } catch (Exception e) {
            log.error("GetAccessToken is fail");
            e.printStackTrace();
        }

        log.info("st = {}", st);
        // 解析相应内容（转换成json对象）
        JSONObject json = null;
        String access_token = null;
        try {
            json = new JSONObject(st);
            // 用户的唯一标识（openid）
            access_token = (String) json.get("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return access_token;
    }

    @Override
    public User getUserInfo(String token){

        //根据token获取openid
        Object json = redisUtil.get(token);
        log.info("json :{}",json);
        User user = JsonUtilByGson.jsonToBean((String) json, User.class);

        String openid = user.getOpenid();

        //数据库查询user
        User findUser = userDao.findByOpenid(openid);

        return findUser;
    }

    @Override
    public User getUserInfoByOpenid(String openid){
        //数据库查询user
        User findUser = userDao.findByOpenid(openid);
        if (findUser==null){
            throw new TBookException(ResultEnum.UNKOWN);
        }
        return findUser;
    }

    @Override
    public User register(String token, User user){
        User saveUser = userDao.save(user);

        //加入redis服务器
        try {
            log.info("saveUser is {}",saveUser);
            redisUtil.set(token, JsonUtilByGson.beanToJson(saveUser), 7200);
        }catch (Exception e){
            log.error("token added fail,token is {}",token);
        }

        return saveUser;
    }
}

