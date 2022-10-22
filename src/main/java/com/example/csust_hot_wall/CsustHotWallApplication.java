package com.example.csust_hot_wall;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.csust_hot_wall.mapper")
@EnableMPP
public class CsustHotWallApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsustHotWallApplication.class, args);
    }

}
