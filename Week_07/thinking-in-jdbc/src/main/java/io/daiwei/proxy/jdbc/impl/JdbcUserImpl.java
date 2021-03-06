package io.daiwei.proxy.jdbc.impl;

import io.daiwei.proxy.annotation.JdbcDS;
import io.daiwei.proxy.datasource.DSUtil;
import io.daiwei.proxy.jdbc.JdbcUser;
import io.daiwei.proxy.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Daiwei on 2021/3/6
 */
public class JdbcUserImpl implements JdbcUser {

    @Override
    public void insertUser(User user) {
        Connection conn = null;
        PreparedStatement state = null;
        try {
            conn = DSUtil.getConn();
            String sql = "insert into user(username, gender, addr) values (?, ?, ?)";
            state = conn.prepareStatement(sql);
            state.setString(1, user.getUsername());
            state.setInt(2, user.getGender());
            state.setString(3, user.getAddr());
            state.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DSUtil.release(conn, state, null);
        }
    }

    @Override
    public User selectUser(Integer id) {
        Connection conn = null;
        PreparedStatement state = null;
        try {
            conn = DSUtil.getConn();
            String sql = "select id, username, gender, addr from user where id = ?";
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            return rs.next() ? new User(rs.getInt("id"), rs.getString("username"),
                    rs.getInt("gender"), rs.getString("addr")) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DSUtil.release(conn, state, null);
        }
        return null;
    }

    @JdbcDS(db = "slave")
    @Override
    public User fetchUser(String name) {
        Connection conn = null;
        PreparedStatement state = null;
        try {
            conn = DSUtil.getConn();
            String sql = "select id, username, gender, addr from user where username = ?";
            state = conn.prepareStatement(sql);
            state.setString(1, name);
            ResultSet rs = state.executeQuery();
            return rs.next() ? new User(rs.getInt("id"), rs.getString("username"),
                    rs.getInt("gender"), rs.getString("addr")) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DSUtil.release(conn, state, null);
        }
        return null;
    }
}
