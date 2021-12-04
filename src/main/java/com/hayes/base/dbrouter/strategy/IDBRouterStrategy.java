package com.hayes.base.dbrouter.strategy;

/**
 * @program: hayes-db-router
 * @Class IDBRouterStrategy
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-04 14:05
 **/
public interface IDBRouterStrategy {
    /**
     * 路由计算
     *
     * @param routerKey 路由字段
     */
    void doRouter(String routerKey);


}
