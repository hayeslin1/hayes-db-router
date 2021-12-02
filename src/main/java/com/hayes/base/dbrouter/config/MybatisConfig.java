package com.hayes.base.dbrouter.config;

import com.hayes.base.dbrouter.dynamic.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: hayes-db-router
 * @Class MybatisConfig
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 19:51
 **/
@Configuration
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);
        return factoryBean.getObject();

    }

}
