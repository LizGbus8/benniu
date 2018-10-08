package com.lzg.common.utlis;

import java.util.ArrayList;

/**
 * 作者：LizG on 2018/9/27 12:12
 * 描述：
 */
public class InterestUtils {
    /**
     * 描述:
     *
     * @return interest简称列表
     * @Param interest数字代码
     */
    public static ArrayList<String> getInterests(Integer integer) {
        String[] strs = null;
        ArrayList<String> list = new ArrayList<>();
        if ((integer & 0x1) == 1) {
            list.add("it");
        }
        if ((integer & 0x2) == 0x2) {
            list.add("en");
        }
        if ((integer & 0x4) == 0x4) {
            list.add("course");
        }
        if ((integer & 0x8) == 0x8) {
            list.add("ky");
        }
        if ((integer & 0x16) == 0x16) {
            list.add("kg");
        }
        if ((integer & 0x32) == 0x32) {
            list.add("mng");
        }
        if ((integer & 0x64) == 0x64) {
            list.add("ec");
        }
        return list;
    }

    /**
     * 描述:
     *
     * @return interest数组代码
     * @Param interest字符串数组
     */
    public static Integer convert2Interest(Integer[] interestArray) {
        Integer interest = 0;
        for (int i = 0; i < interestArray.length; i++) {
            interest = interest | interestArray[i];
        }
        return interest;
    }
}
