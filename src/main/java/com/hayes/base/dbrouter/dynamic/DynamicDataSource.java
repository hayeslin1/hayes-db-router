package com.hayes.base.dbrouter.dynamic;

import com.hayes.base.dbrouter.config.DBContextHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @program: hayes-db-router
 * @Class MyRoutingDataSource
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 14:29
 **/
@Log4j2
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        try {
            String dbRouter = DBContextHolder.getDatasourceRouter();
            log.info("当前数据源：{}", dbRouter);
            return dbRouter;
        } finally {
            DBContextHolder.removeDatasourceRouter();
        }
    }
}
