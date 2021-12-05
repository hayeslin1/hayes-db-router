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

    /**
     * 路由之后的表名：test_000 , test_001：
     */

    @Insert("insert into test (id, u_id) values (#{id},#{uId})")
    @DBRouter(routerKey = "id")
    int insert(Long id, Long uId);

    @Select("select * from test where id = #{id} ")
    @DBRouter(routerKey = "id")
    List<Map<String, Object>> select(Long id);


}
