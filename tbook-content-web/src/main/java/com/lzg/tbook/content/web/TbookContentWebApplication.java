package com.lzg.tbook.content.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.lzg.manager", "com.lzg.common"})
public class TbookContentWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookContentWebApplication.class, args);
    }
}
