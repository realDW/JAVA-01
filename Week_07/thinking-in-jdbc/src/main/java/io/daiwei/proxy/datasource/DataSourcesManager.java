package io.daiwei.proxy.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Daiwei on 2021/3/6
 */

@Log4j
public class DataSourcesManager {

    private static final String SPLIT = ",";

    private static volatile DataSourcesManager dataSourcesConfig = null;

    public final String masterName;

    private static final AtomicInteger counter = new AtomicInteger();

    @Getter
    private final Map<String, DataSource> dataSourceMap;

    private DataSourcesManager(Map<String, DataSource> ds) {
        dataSourceMap = ds;
        masterName = ds.keySet().stream().findFirst().get();
    }

    /**
     * 单例加载出多数据源
     * @return
     */
    public static DataSourcesManager getInstance() {
        if (dataSourcesConfig == null) {
            synchronized (DataSourcesManager.class) {
                if (dataSourcesConfig == null) {
                    dataSourcesConfig = new DataSourcesManager(initHikariDataSources());
                    return dataSourcesConfig;
                }
            }
        }
        return dataSourcesConfig;
    }

    /**
     * 初始化 HikariDataSources
     * @return
     */
    private static Map<String, DataSource> initHikariDataSources() {
        Map<String, DataSource> map = new TreeMap<>();
        try {
            Properties prop = new Properties();
            InputStream is = DataSourcesManager.class.getClassLoader()
                    .getResourceAsStream("datasources.properties");
            prop.load(is);
            String nameStr = prop.getProperty("jdbc.datasource.names");
            String[] names = nameStr.split(SPLIT);
            for (String name : names) {
                map.put(name.trim(), initSingleDatasource(name.trim(), prop));
            }
        } catch (Exception e) {
           log.error("init data sources error!");
           e.printStackTrace();
        }
        return map;
    }

    /**
     * 初始化单个数据库连接池
     * @return
     */
    private static DataSource initSingleDatasource(String name, Properties prop) {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(prop.getProperty("jdbc."+ name +".driver"));
        ds.setJdbcUrl(prop.getProperty("jdbc."+ name +".url"));
        ds.setUsername(prop.getProperty("jdbc."+ name +".username"));
        ds.setPassword(prop.getProperty("jdbc."+ name +".password"));
        return ds;
    }

    /**
     * 获取轮询的SlaveName
     * @return
     */
    public String getRobinSlaveName() {
        if (dataSourceMap.size() == 1) {
            return masterName;
        }
        int idx = counter.getAndIncrement() % (dataSourceMap.size() - 1);
        List<String> nameList = dataSourceMap.keySet().stream().skip(1).collect(Collectors.toList());
        return nameList.get(idx);
    }
}
