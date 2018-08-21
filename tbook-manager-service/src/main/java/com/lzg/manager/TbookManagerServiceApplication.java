package com.lzg.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EntityScan(basePackages={"com.lzg.manager.com.lzg.tbook.search.entity"})
public class TbookManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookManagerServiceApplication.class, args);
    }
}
