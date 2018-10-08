package com.lzg.tbook.order.listener;

import com.lzg.order.dto.WXmsg4Buyer;
import com.lzg.order.dto.WXmsg4Saller;
import org.springframework.stereotype.Service;

/**
 * 作者：LizG on 2018/10/3 21:38
 * 描述：
 */
@Service
public class WXmessageListener {


    public void pushMsg2Buyer(WXmsg4Buyer wXmsg4Buyer){
        /*
        * 1.token
        * 2.template
        * 3.page
        *
        * */
    }

    public void pushMsg2Saller(WXmsg4Saller wXmsg4Saller){

    }

}
