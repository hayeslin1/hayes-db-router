package com.hayes.base.dbrouter;

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
 * @Class TestDB
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
        dbMapper.insert();
    }


    @Test
    public void testSelectWithMaster() {
        dbMapper.selectWithMaster();
    }

    @Test
    public void testSelect() {
        for (int i = 0; i < 1; i++) {
            List<Map<String, Object>> select = dbMapper.select(2);
            log.info(select);
        }
    }
}
