package com.lzg.tbook.publish;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzg.tbook.publish.mapper")
public class TbookPublishServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookPublishServiceApplication.class, args);
    }
}
