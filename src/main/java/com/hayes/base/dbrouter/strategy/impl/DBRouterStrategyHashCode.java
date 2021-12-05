package com.hayes.base.dbrouter.strategy.impl;

import com.hayes.base.dbrouter.config.DBContextHolder;
import com.hayes.base.dbrouter.entity.RouterEntity;
import com.hayes.base.dbrouter.strategy.IDBRouterStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @program: hayes-db-router
 * @Class DBRouterStrategyHashCode
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-04 14:07
 **/
@Log4j2
@Component
public class DBRouterStrategyHashCode implements IDBRouterStrategy {

    private RouterEntity routerEntity;

    public DBRouterStrategyHashCode(RouterEntity routerEntity) {
        this.routerEntity = routerEntity;
    }

    /**
     * 路由计算
     * 搬运傅哥的算法
     *
     * @param routerValue 路由字段值
     */
    @Override
    public void doRouter(String routerValue) {

        int dbCount = routerEntity.getDbNames().split(",").length - 1;

        int tbCount = routerEntity.getTbCount();

        int size = dbCount * tbCount;

        // 扰动函数
        int idx = (size - 1) & (routerValue.hashCode() ^ (routerValue.hashCode() >>> 16));

        // 库表索引
        int dbIdx = idx / tbCount + 1;
        int tbIdx = idx - tbCount * (dbIdx - 1);

        // 设置到 ThreadLocal
        DBContextHolder.setDatasourceRouter(String.format("%02d", dbIdx));
        DBContextHolder.setTableRouter(String.format("%03d", tbIdx));
        log.debug("数据库路由 dbIdx：{} tbIdx：{}", dbIdx, tbIdx);

    }
}
