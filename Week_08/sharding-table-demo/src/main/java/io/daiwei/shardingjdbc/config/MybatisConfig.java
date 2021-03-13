package io.daiwei.shardingjdbc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Daiwei on 2021/3/13
 */
@MapperScan("io.daiwei.shardingjdbc.mapper")
@Configuration
public class MybatisConfig {
}
