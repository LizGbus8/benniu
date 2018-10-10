package com.lzg.tbook.search;

import com.lzg.tbook.search.listener.ApplicationStartup;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzg.tbook.search.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class TbookSearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TbookSearchServiceApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
