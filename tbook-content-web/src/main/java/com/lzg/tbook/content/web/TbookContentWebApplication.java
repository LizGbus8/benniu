package com.lzg.tbook.content.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TbookContentWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookContentWebApplication.class, args);
    }
}
