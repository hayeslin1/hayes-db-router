package com.hayes.base.dbrouter.mapper;

import com.hayes.base.dbrouter.annotation.DBRouter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @program: hayes-db-router
 * @interface DBMapper
 * @description: 关于此接口的描述说明
 * @author: Mr.HayesLin
 * @create: 2021-12-02 15:18
 **/
@Mapper
public interface DBMapper {


    @Insert("insert into test values (1,\"tom\")")
    int insert();


    @Select("select * from test")
    List<Map<String, Object>> selectWithMaster();


    @Select("select * from test1 where id = #{id} ")
    @DBRouter(routerKey = "id")
    List<Map<String, Object>> select(Integer id);



}
