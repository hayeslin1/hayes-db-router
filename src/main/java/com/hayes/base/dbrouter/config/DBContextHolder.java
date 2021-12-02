package com.hayes.base.dbrouter.config;

import com.hayes.base.dbrouter.enums.DBTypeEnum;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: hayes-db-router
 * @Class DBContextHolder
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 14:43
 **/
@Log4j2
public class DBContextHolder {


    private static final ThreadLocal<DBTypeEnum> contentHolder = new ThreadLocal<>();
    private static final AtomicInteger counter = new AtomicInteger(0);


    public static void set(DBTypeEnum dbType) {
        contentHolder.set(dbType);
    }

    public static void remove() {
        contentHolder.remove();
    }

    public static DBTypeEnum get() {
        return contentHolder.get();
    }


    public static void master() {
        set(DBTypeEnum.MASTER);
        log.info("db router to master !! ");
    }

    public static void slave() {
        if (counter.get() > 999) {
            counter.set(0);
        }
        if (counter.getAndIncrement() % 2 == 0) {
            set(DBTypeEnum.SLAVE1);
            log.info("db router to slave1 !! ");
        } else {
            set(DBTypeEnum.SLAVE2);
            log.info("db router to slave2 !! ");
        }
    }


}
