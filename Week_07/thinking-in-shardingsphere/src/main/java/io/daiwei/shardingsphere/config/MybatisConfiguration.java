package io.daiwei.shardingsphere.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Daiwei on 2021/3/9
 */
@Configuration
@MapperScan("io.daiwei.shardingsphere.mapper")
public class MybatisConfiguration {
}
