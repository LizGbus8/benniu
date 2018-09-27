package com.lzg.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages={"com.lzg.manager.entity"})
public class TbookSsoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookSsoServiceApplication.class, args);
    }
}
