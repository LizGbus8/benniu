package com.lzg.tbook.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages={"com.lzg.product.entity"})
public class TbookProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookProductServiceApplication.class, args);
    }
}
