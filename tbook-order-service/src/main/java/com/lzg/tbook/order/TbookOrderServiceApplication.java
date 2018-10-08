package com.lzg.tbook.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages={"com.lzg.order.com.lzg.tbook.search.entity","com.lzg.order.entity"})
@ComponentScan(basePackages = {"com.lzg.common"})
public class TbookOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TbookOrderServiceApplication.class, args);
    }
}
