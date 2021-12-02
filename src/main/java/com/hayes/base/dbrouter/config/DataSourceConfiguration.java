package com.hayes.base.dbrouter.config;

import com.hayes.base.dbrouter.dynamic.DynamicDataSource;
import com.hayes.base.dbrouter.enums.DBTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @program: hayes-db-router
 * @Class DataSourceConfiguration
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 14:22
 **/
@Component
@Configuration
public class DataSourceConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSource slave2DataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public DataSource dataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slave1DataSource") DataSource slave1DataSource,
            @Qualifier("slave2DataSource") DataSource slave2DataSource) {

        HashMap<Object, Object> target = new HashMap<>();

        target.put(DBTypeEnum.MASTER, masterDataSource);
        target.put(DBTypeEnum.SLAVE1, slave1DataSource);
        target.put(DBTypeEnum.SLAVE2, slave2DataSource);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        dynamicDataSource.setTargetDataSources(target);

        return dynamicDataSource;

    }

}
