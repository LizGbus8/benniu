package com.lzg.manager;

import com.lzg.manager.listener.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lzg.manager", "com.lzg.common"})
@EntityScan(basePackages = {"com.lzg.manager.com.lzg.tbook.search.entity", "com.lzg.manager.entity"})
public class TbookManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TbookManagerServiceApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }

}
