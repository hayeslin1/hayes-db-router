package com.hayes.base.dbrouter.config;

import lombok.extern.log4j.Log4j2;

/**
 * @program: hayes-db-router
 * @Class DBContextHolder
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 14:43
 **/
@Log4j2
public class DBContextHolder {

    /**
     * 数据库路由
     */
    private static final ThreadLocal<String> datasourceRouter = new ThreadLocal<>();
    /**
     * 表路由
     */
    private static final ThreadLocal<String> tableRouter = new ThreadLocal<>();


    public static void setDatasourceRouter(String router) {
        datasourceRouter.set(router);
    }

    public static void setTableRouter(String router) {
        tableRouter.set(router);
    }

    public static String getDatasourceRouter() {
        return datasourceRouter.get();
    }

    public static String getTableRouter() {
        return tableRouter.get();
    }

    public static void removeDatasourceRouter() {
        datasourceRouter.remove();
    }

    public static void removeTableRouter() {
        tableRouter.remove();
    }

}
