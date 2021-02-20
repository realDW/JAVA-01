package io.daiwei.jdbc.test;

import io.daiwei.jdbc.pojo.User;
import io.daiwei.jdbc.util.JdbcUtil;

import java.sql.*;
import java.util.List;

/**
 * Created by Daiwei on 2021/2/20
 */
public class JdbcPreStateTest {

    public JdbcPreStateTest() {}

    /**
     * 插入一个User
     * @param user
     */
    public static void insertUser(User user) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
//            conn = JdbcUtil.getConnection();
            conn = JdbcUtil.getConnFromHikari();
            String sql = "insert into user(username, age, addr) values(?, ?, ?)";
            stat = conn.prepareStatement(sql);
            stat.setString(1, user.getUsername());
            stat.setInt(2, user.getAge());
            stat.setString(3, user.getAddr());
            stat.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, stat, null);
        }
    }

    /**
     * 批处理
     * @param list
     */
    public static void insertBatch(List<User> list) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = JdbcUtil.getConnFromHikari();
            conn.setAutoCommit(false);
            String sql = "insert into user(username, age, addr) values(?, ?, ?)";
            stat = conn.prepareStatement(sql);
            for (User user : list) {
                stat.setString(1, user.getUsername());
                stat.setInt(2, user.getAge());
                stat.setString(3, user.getAddr());
                stat.addBatch();
            }
            stat.executeBatch();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, stat, null);
        }
    }

    /**
     * 修改User 事务操作
     * @param user
     */
    public static void updateUser(User user) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
//            conn = JdbcUtil.getConnection();
//          开启事务
            conn.setAutoCommit(false);
            // 第一个更新
            conn = JdbcUtil.getConnFromHikari();
            String sql = "update user set username=?, age=?, addr=? where id =?";
            stat = conn.prepareStatement(sql);
            stat.setString(1, user.getUsername());
            stat.setInt(2, user.getAge());
            stat.setString(3, user.getAddr());
            stat.setLong(4, user.getId());
            stat.execute();
            // 第二个更新
            sql = "update user set username=?, age=?, addr=? where id =?";
            stat = conn.prepareStatement(sql);
            stat.setString(1, user.getUsername());
            stat.setInt(2, user.getAge());
            stat.setString(3, user.getAddr());
            stat.setLong(4, 2L);
            stat.execute();
//            提交事务
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, stat, null);
        }
    }

    /**
     * 根据Id查询User
     * @param userId
     * @return
     */
    public static User selectUserById(Long userId) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select id, username, age, addr from user where id = ?";
            stat = conn.prepareStatement(sql);
            stat.setLong(1, userId);
            rs = stat.executeQuery();
            return rs.next() ? new User(rs.getLong("id"), rs.getString("username"),
                    rs.getInt("age"), rs.getString("addr")) :null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        } finally {
            JdbcUtil.release(conn, stat, rs);
        }
    }

    /**
     * 根据 Id 删除 user
     * @param userId
     */
    public static void deleteUserById(Long userId) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "delete from user where id =?";
            stat = conn.prepareStatement(sql);
            stat.setLong(1, userId);
            stat.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, stat, null);
        }
    }
}
