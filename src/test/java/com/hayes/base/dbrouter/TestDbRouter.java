package com.hayes.base.dbrouter;

import cn.hutool.core.util.IdUtil;
import com.hayes.base.dbrouter.mapper.DBMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @program: hayes-db-router
 * @Class TestDbRouter
 * @description: 关于此类的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 15:24
 **/
@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDbRouter {

    @Autowired
    private DBMapper dbMapper;

    @Test
    public void testInsert() {
        for (int i = 0; i < 1000; i++) {
            dbMapper.insert(IdUtil.getSnowflake().nextId(), (long) i);
        }
    }

    @Test
    public void testSelect() {
        List<Map<String, Object>> select = dbMapper.select(1467333932642553856L);
        log.info(select);
    }
}
