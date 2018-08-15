package com.lzg.tbook.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages={"com.lzg.order.entity"})
public class TbookOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TbookOrderServiceApplication.class, args);
    }
}
