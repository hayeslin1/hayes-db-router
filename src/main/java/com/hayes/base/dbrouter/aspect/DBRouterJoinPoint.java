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

        // 路由属性
        String routerKeyAttr = getAttrValue(routerKey, pjp.getArgs());
        // 路由策略
        dbRouterStrategy.doRouter(routerKeyAttr);

        try {
            return pjp.proceed();
        } finally {
            DBContextHolder.removeDatasourceRouter();
            DBContextHolder.removeTableRouter();
        }
    }

    public String getAttrValue(String attr, Object[] args) {
        if (1 == args.length) {
            Object arg = args[0];
            if (arg instanceof String) {
                return arg.toString();
            }
        }

        String filedValue = null;
        for (Object arg : args) {
            try {
                if (!StringUtils.isEmpty(filedValue)) {
                    break;
                }
                filedValue = BeanUtils.getProperty(arg, attr);
            } catch (Exception e) {
                log.error("获取路由属性值失败 attr：{}", attr, e);
            }
        }
        return filedValue;
    }

}
