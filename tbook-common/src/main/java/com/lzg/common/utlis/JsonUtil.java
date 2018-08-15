package com.lzg.common.utlis;

import java.util.Map;

/**
 * 作者：LizG on 2018/8/2 21:03
 * 描述：
 */
public interface JsonUtil {
    /**
     * Bean对象转JSON
     *
     * @param object
     * @param dataFormatString
     * @return
     */
    String beanToJson(Object object);


    /**
     * 将json字符串转换成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    Object jsonToBean(String json, Object clazz);


    /**
     * json字符串转map
     *
     * @param json
     * @return
     */
    Map<String, Object> jsonToMap(String json);
}
