package io.daiwei.multids.configuration;

import com.zaxxer.hikari.HikariDataSource;
import io.daiwei.multids.configuration.properties.DataSourceProp;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Daiwei on 2021/3/6
 */
@Configuration
@MapperScan("io.daiwei.multids.mapper")
public class DynamicDataSourceConfiguration {

    private static final String SPLIT = ",";

    @Resource
    private Environment env;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        String names = env.getProperty("datasource.names");
        Assert.hasText(names, "datasource is necessary");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource master = null;
        for (String name : names.split(SPLIT)) {
            DataSourceProp dataSourceProp = sourceProp(name.trim());
            DataSource dataSource = dataSource(dataSourceProp);
            if (master == null) {
                master = dataSource;
                dynamicDataSource.setDefaultTargetDataSource(dataSource);
            }
            DataSourceHolder.addDataSource(name.trim());
            dataSourceMap.put(name.trim(), dataSource);
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource());
        org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources(Objects.requireNonNull(env.getProperty("mybatis.mapper-locations")));
        sqlSessionFactory.setMapperLocations(resources);
        return sqlSessionFactory.getObject();
    }


    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    private DataSourceProp sourceProp(String name) {
        DataSourceProp prop = new DataSourceProp();
        prop.setDriver(env.getProperty("datasource." + name + ".driver", String.class));
        prop.setUrl(env.getProperty("datasource." + name + ".url", String.class));
        prop.setUsername(env.getProperty("datasource." + name + ".username", String.class));
        prop.setPassword(env.getProperty("datasource." + name + ".password", String.class));
        return prop;
    }

    private DataSource dataSource(DataSourceProp prop) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(prop.getUrl());
        dataSource.setDriverClassName(prop.getDriver());
        dataSource.setUsername(prop.getUsername());
        dataSource.setPassword(prop.getPassword());
        return dataSource;
    }
}
