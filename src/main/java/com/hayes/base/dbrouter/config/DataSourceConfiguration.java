//package com.hayes.base.dbrouter.config;
//
//import com.hayes.base.dbrouter.dynamic.DynamicDataSource;
//import com.hayes.base.dbrouter.dynamic.DynamicSQLMybatisPlugin;
//import com.hayes.base.dbrouter.entity.RouterEntity;
//import com.hayes.base.dbrouter.util.DataSourceUtil;
//import com.hayes.base.dbrouter.util.PropertyUtil;
//import lombok.extern.log4j.Log4j2;
//import org.apache.ibatis.plugin.Interceptor;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * @program: hayes-db-router
// * @Class DataSourceConfiguration
// * @description: 关于此类的描述说明
// * @author: Mr.HayesLin
// * @create: 2021-12-02 14:22
// **/
//@Log4j2
//@Component
//@Configuration
//public class DataSourceConfiguration implements EnvironmentAware {
//
//    private RouterEntity routerEntity;
//
//    public DataSourceConfiguration(RouterEntity routerEntity) {
//        this.routerEntity = routerEntity;
//    }
//
//    private final HashMap<Object, Object> target = new HashMap<>();
//
//    @Bean
//    public DataSource dataSource() {
//
//
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        dynamicDataSource.setTargetDataSources(target);
//        dynamicDataSource.setDefaultTargetDataSource(routerEntity.getDbDefault());
//
//        return dynamicDataSource;
//
//    }
//
//    @Bean
//    public Interceptor plugin() {
//        return new DynamicSQLMybatisPlugin();
//    }
//
//    @Override
//    public void setEnvironment(final Environment environment) {
//
//        String dbNames = Optional.ofNullable(routerEntity.getDbNames()).orElseThrow(NullPointerException::new);
//        try {
//            for (String dbName : dbNames.split(",")) {
//                target.put(dbName, getDataSource(environment, dbName));
//            }
//            if (null == routerEntity.getDbDefault()) {
//                routerEntity.setDbDefault(getDataSource(environment, RouterEntity.DEFAULT));
//            }
//
//        } catch (final ReflectiveOperationException ex) {
//            throw new RuntimeException("ReflectiveOperationException ! Can't find data source type.", ex);
//        } catch (final NamingException ex) {
//            throw new RuntimeException("NamingException ! Can't find JNDI data source.", ex);
//        }
//    }
//
//    public DataSource getDataSource(final Environment environment, String dbName) throws ReflectiveOperationException, NamingException {
//        String dataSource = environment.getProperty(String.format("%s.%s", RouterEntity.PREFIX, dbName));
//        assert dataSource != null;
//        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, dataSource, Map.class);
//        DataSource result = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
//        return result;
//    }
//}
