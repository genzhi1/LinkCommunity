package com.yxf.linkcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yxf.mapper")
public class LinkCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkCommunityApplication.class, args);
    }

}
