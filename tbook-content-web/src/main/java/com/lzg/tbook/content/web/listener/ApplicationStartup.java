package com.lzg.tbook.content.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Timer;

/**
 * 作者：LizG on 2018/9/27 10:35
 * 描述：
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        HotUpdateTask task = event.getApplicationContext().getBean(HotUpdateTask.class);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task,5000*10,90*1000*60);
    }
}
