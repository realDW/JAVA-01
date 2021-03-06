package io.daiwei.jdbc.util;

import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Daiwei on 2021/2/20
 */
public class JdbcUtil {

    private static final HikariDataSource ds = new HikariDataSource();

    static {
        initHikariDs(ds);
    }

    private JdbcUtil() {}

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() {
        Connection cnn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnn = DriverManager.getConnection("jdbc:mysql://192.168.199.101:3306/hello?characterEncoding=utf-8",
                    "root", "daiwei123");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cnn;
    }

    /**
     * 替换使用 Hikari 数据库链接池
     * @return
     */
    public static Connection getConnFromHikari() {
        Connection cnn = null;
        try {
            cnn = ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cnn;
    }

    /**
     * 关闭连接
     * @param conn
     * @param statement
     */
    public static void release(Connection conn, Statement statement, ResultSet resultSet) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void initHikariDs(HikariDataSource ds) {
        InputStream is = null;
        try {
            Properties prop = new Properties();
            is = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(is);
            ds.setDriverClassName(prop.getProperty("jdbc.driver"));
            ds.setJdbcUrl(prop.getProperty("jdbc.url"));
            ds.setUsername(prop.getProperty("jdbc.username"));
            ds.setPassword(prop.getProperty("jdbc.password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
