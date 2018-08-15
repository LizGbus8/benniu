package com.lzg.common.utlis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 作者：LizG on 2018/8/2 21:14
 * 描述：
 */
public class JsonUtilByGson {

    private static final Gson gson = new Gson();

    /**
     * 描述: 对象转json
     *
     * @return
     * @Param
     */
    public static String beanToJson(Object object) {
        if (object != null) {
            return gson.toJson(object);
        }
        return null;
    }

    /**
     * 描述: json2bean
     * @Param
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        T t = null;
        if (json != null) {
            t = gson.fromJson(json, clazz);
        }
        return t;
    }

    /**
     * 描述:
     * @Param
     * @return
     */
    public static <T> T jsonTobean(String json,Type type){
        if (json != null){
            return gson.fromJson(json,type);
        }
        return null;
    }

    public static <T> T objectTolist(Object object,Type type){
        if (object != null){
            String json = gson.toJson(object, type);
            return gson.fromJson(json, type);
        }
        return null;
    }
}
