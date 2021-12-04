package com.hayes.base.dbrouter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @program: hayes-db-router
 * @Class RouterEntity
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-04 12:10
 **/
@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = RouterEntity.PREFIX)
public class RouterEntity {

    public static final String PREFIX = "spring.hayeslin.datasource";
    public static final String DEFAULT = "dbDefault";


    private String dbNames;
    private String routerKey;
    private Integer tbCount;
    private DataSource dbDefault;






}

