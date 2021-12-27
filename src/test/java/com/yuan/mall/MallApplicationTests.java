package com.yuan.mall;

import com.alibaba.druid.pool.DruidDataSource;
import com.yuan.mall.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    DataSource dataSource;

    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate template;

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() throws SQLException {
        //看一下默认数据源
        System.out.println(dataSource.getClass());
        //获得连接
        DruidDataSource druidDataSource =   (DruidDataSource)dataSource;
        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());

        //关闭连接
        druidDataSource.close();
    }

    @Test
    public void connectToRedisTest(){
        redisUtil.set("diaoyuan", "hellodiaoyuan");
        System.out.println(redisUtil.get("diaoyuan"));
    }

}
