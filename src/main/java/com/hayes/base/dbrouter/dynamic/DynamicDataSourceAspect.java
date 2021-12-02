package com.hayes.base.dbrouter.dynamic;


import com.hayes.base.dbrouter.config.DBContextHolder;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description: Mybatis 拦截器
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/30
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Log4j2
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Pointcut("!@annotation(com.hayes.base.dbrouter.annotation.Master) " +
            "&& (execution(* com.hayes.base.dbrouter.mapper.*.select*(..)))")
    public void readPointcut() {
        log.info("readPointcut");
    }

    @Pointcut("@annotation(com.hayes.base.dbrouter.annotation.Master) " +
            "|| (execution(* com.hayes.base.dbrouter.mapper.*.insert*(..)) " +
            "|| execution(* com.hayes.base.dbrouter.mapper.*.update*(..)))")
    public void writePointcut() {
        log.info("writePointcut");
    }


    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }


}
