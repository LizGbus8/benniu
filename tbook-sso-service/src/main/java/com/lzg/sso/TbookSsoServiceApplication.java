package com.lzg.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages={"com.lzg.manager.entity"})
@ComponentScan(basePackages = {"com.lzg.common","com.lzg.sso"})
public class TbookSsoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbookSsoServiceApplication.class, args);
    }
}
