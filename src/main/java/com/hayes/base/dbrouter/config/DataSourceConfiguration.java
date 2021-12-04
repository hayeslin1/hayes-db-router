package com.hayes.base.dbrouter.config;

import com.hayes.base.dbrouter.dynamic.DynamicDataSource;
import com.hayes.base.dbrouter.dynamic.DynamicSQLMybatisPlugin;
import com.hayes.base.dbrouter.entity.RouterEntity;
import com.hayes.base.dbrouter.util.DataSourceUtil;
import com.hayes.base.dbrouter.util.PropertyUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @program: hayes-db-router
 * @Class DataSourceConfiguration
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 14:22
 **/
@Log4j2
@Component
@Configuration
public class DataSourceConfiguration implements EnvironmentAware {

    private RouterEntity routerEntity;

    public DataSourceConfiguration(RouterEntity routerEntity) {
        this.routerEntity = routerEntity;
    }

    private final HashMap<Object, Object> target = new HashMap<>();


    @Bean
    public Interceptor plugin() {
        return new DynamicSQLMybatisPlugin();
    }

    @Bean
    public DataSource dataSource() {


        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(target);
        dynamicDataSource.setDefaultTargetDataSource(target.get(routerEntity.getDbNames().split(",")[0]));

        return dynamicDataSource;

    }

    @Override
    public void setEnvironment(Environment environment) {

        String dbNames = Optional.ofNullable(routerEntity.getDbNames()).orElseThrow(NullPointerException::new);
        try {
            for (String dbName : dbNames.split(",")) {
                target.put(dbName, getDataSource(environment, dbName));
            }
        } catch (final ReflectiveOperationException ex) {
            throw new RuntimeException("ReflectiveOperationException ! Can't find data source type.", ex);
        } catch (Exception e) {
            throw new RuntimeException("未知错误", e);
        }
    }

    public DataSource getDataSource(Environment environment, String dbName) throws ReflectiveOperationException {
        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, String.format("%s.%s", RouterEntity.PREFIX, dbName), Map.class);
        DataSource result = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
        return result;
    }


}
