package com.hayes.base.dbrouter.aspect;

import com.hayes.base.dbrouter.annotation.DBRouter;
import com.hayes.base.dbrouter.config.DBContextHolder;
import com.hayes.base.dbrouter.entity.RouterEntity;
import com.hayes.base.dbrouter.strategy.IDBRouterStrategy;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @program: hayes-db-router
 * @Class DBRouterJoinPoint
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-04 14:28
 **/
@Aspect
@Log4j2
@Component
public class DBRouterJoinPoint {

    private RouterEntity routerEntity;

    private IDBRouterStrategy dbRouterStrategy;

    public DBRouterJoinPoint(RouterEntity routerEntity, IDBRouterStrategy dbRouterStrategy) {
        this.routerEntity = routerEntity;
        this.dbRouterStrategy = dbRouterStrategy;
    }

    @Pointcut("@annotation(com.hayes.base.dbrouter.annotation.DBRouter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint pjp, DBRouter dbRouter) throws Throwable {

        String routerKey = dbRouter.routerKey();

        if (StringUtils.isEmpty(routerKey) && StringUtils.isEmpty(routerEntity.getRouterKey())) {
            throw new RuntimeException("annotation DBRouter routerKey is null！");
        }

        routerKey = StringUtils.isEmpty(routerKey) ? routerEntity.getRouterKey() : routerKey;
        log.debug("执行策略：{}分库， {}分表, 路由字段：{}", dbRouter.splitDatabase() ? "" : "不", dbRouter.splitTable() ? "" : "不", routerKey);
        // 路由属性
        String routerKeyValue = getAttrValue(routerKey, pjp.getArgs());
        // 路由策略
        dbRouterStrategy.doRouter(routerKeyValue);

        // 判断配置是否分库
        if (!dbRouter.splitDatabase()) {
            DBContextHolder.removeDatasourceRouter();
        }
        try {
            return pjp.proceed();
        } finally {
            DBContextHolder.removeTableRouter();
        }
    }

    public String getAttrValue(String attr, Object[] args) {
        if (args.length == 0) {
            return null;
        }
        for (Object arg : args) {
            try {
                if (arg instanceof Integer
                        || arg instanceof Long
                        || arg instanceof String) {
                    return arg.toString();
                } else {
                    return BeanUtils.getProperty(arg, attr);
                }
            } catch (Exception e) {
                log.error("获取路由属性值失败 attr：{}", attr, e);
                throw new RuntimeException("获取路由属性值失败：" + attr, e);
            }
        }
        return null;
    }

}
