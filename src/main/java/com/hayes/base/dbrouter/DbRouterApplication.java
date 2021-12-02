package com.hayes.base.dbrouter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: hayes-db-router
 * @Class DbRouterApplication
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 12:09
 **/
@SpringBootApplication
@MapperScan("com.hayes.base.**.mapper")
public class DbRouterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbRouterApplication.class, args);
    }
}
