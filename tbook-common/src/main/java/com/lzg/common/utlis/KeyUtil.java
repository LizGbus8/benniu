package com.lzg.common.utlis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 作者：LizG on 2018/7/23 16:34
 * 描述：主键，ID，唯一标识的生成工具
 */
public class KeyUtil {

    /**
     * 生成时间
     */
    private static Date createTime = new Date();
    /**
     * 后缀随机数字 6位
     */
    private static Integer number;

    public static synchronized String getKey() {
        StringBuffer key = new StringBuffer();
        long millis = System.currentTimeMillis();
        createTime.setTime(millis);
        SimpleDateFormat pattern=new SimpleDateFormat("yyyymmddHHmmss");
        String time = pattern.format(createTime);
        key.append(time);
        Random random = new Random();
        number = random.nextInt(900000) + 100000;
        key.append(number.toString());
        return key.toString();
    }
}
