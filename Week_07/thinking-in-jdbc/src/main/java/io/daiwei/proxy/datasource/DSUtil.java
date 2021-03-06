package io.daiwei.proxy.datasource;

import io.daiwei.jdbc.util.JdbcUtil;
import lombok.extern.log4j.Log4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Daiwei on 2021/3/6
 */
@Log4j
public class DSUtil {

    private DSUtil() {}

    public static Connection getConn() {
        String dsName = DataSourceHolder.getDsName(DataSourcesManager.getInstance().masterName);
        DataSource dataSource = DataSourcesManager.getInstance().getDataSourceMap().get(dsName);
        if (dataSource == null) {
            throw new RuntimeException("get dataSource["+ dsName +"] failed");
        }
        Connection conn = null;
        try {
            conn =  dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("pick datasource -> " + dsName);
        return conn;
    }

    public static void release(Connection conn, Statement statement, ResultSet resultSet) {
        JdbcUtil.release(conn, statement, resultSet);
    }
}
