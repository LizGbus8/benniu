package com.lzg.tbook.content.web;

import com.lzg.tbook.content.web.listener.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.lzg.manager", "com.lzg.common"})
public class TbookContentWebApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TbookContentWebApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
