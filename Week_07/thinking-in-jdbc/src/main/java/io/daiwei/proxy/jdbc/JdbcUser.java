package io.daiwei.proxy.jdbc;

import io.daiwei.proxy.pojo.User;

/**
 * Created by Daiwei on 2021/3/6
 */
public interface JdbcUser {

    void insertUser(User user);

    User selectUser(Integer id);

    User fetchUser(String name);

}
