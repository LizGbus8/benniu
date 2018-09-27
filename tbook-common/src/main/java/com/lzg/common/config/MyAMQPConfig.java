package com.lzg.common.config;

import com.lzg.common.converter.Gson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作者：LizG on 2018/8/4 21:23
 * 描述：
 */
@Configuration
public class MyAMQPConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Gson2JsonMessageConverter();
    }
}
