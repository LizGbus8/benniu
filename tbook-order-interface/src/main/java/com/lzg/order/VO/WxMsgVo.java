package com.lzg.order.VO;

import lombok.Data;

import java.util.Map;

/**
 * 作者：LizG on 2018/10/3 21:48
 * 描述：
 */
@Data
public class WxMsgVo {
    /** 接收者openid */
    private String touser;

    /** 消息模板id */
    private String template_id;

    /** 点击卡片跳转到的页面 */
    private String page;

    /** 表单的id */
    private String form_id;

    /** 表单数据 data */
    private Map<String,Map<String,Object>> data;
}
