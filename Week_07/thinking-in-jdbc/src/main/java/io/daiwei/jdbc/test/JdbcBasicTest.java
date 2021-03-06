package io.daiwei.jdbc.test;

import io.daiwei.jdbc.pojo.User;
import io.daiwei.jdbc.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Daiwei on 2021/2/20
 */
public class JdbcBasicTest {

    private JdbcBasicTest() {}

    /**
     * 插入一个User
     * @param user
     */
    public static void insertUser(User user) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into user(id, username, age, addr) values(" +user.getId() +", '"
                    + user.getUsername() +"', "+ user.getAge() +", '"+ user.getAddr() +"')";
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, statement, null);
        }
    }

    /**
     * 修改User
     * @param user
     */
    public static void updateUser(User user) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "update user set username='" + user.getUsername() + "', age=" + user.getAge() +", addr='"+ user.getAddr() +"' where id =" + user.getId();
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, statement, null);
        }
    }

    /**
     * 根据Id查询User
     * @param userId
     * @return
     */
    public static User selectUserById(Long userId) {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            statement = conn.createStatement();
            String sql = "select id, username, age, addr from user where id = " + userId;
            rs = statement.executeQuery(sql);
            return rs.next() ? new User(rs.getLong("id"), rs.getString("username"),
                    rs.getInt("age"), rs.getString("addr")) :null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        } finally {
            JdbcUtil.release(conn, statement, rs);
        }
    }

    /**
     * 根据 Id 删除 user
     * @param userId
     */
    public static void deleteUserById(Long userId) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = JdbcUtil.getConnection();
            statement = conn.createStatement();
            String sql = "delete from user where id =" + userId;
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, statement, null);
        }
    }
}
