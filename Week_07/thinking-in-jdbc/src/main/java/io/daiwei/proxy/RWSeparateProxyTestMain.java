package io.daiwei.proxy;

import io.daiwei.proxy.cglib.JdbcRWSeparateProxy;
import io.daiwei.proxy.jdbc.impl.JdbcUserImpl;
import io.daiwei.proxy.pojo.User;

/**
 * JDBC 读写分离测试
 * Created by Daiwei on 2021/3/6
 */
public class RWSeparateProxyTestMain {

    public static void main(String... args) {
        JdbcRWSeparateProxy proxy = new JdbcRWSeparateProxy();
        JdbcUserImpl jdbcUser = proxy.getInstance(JdbcUserImpl.class);
//        jdbcUser.insertUser(new User("xiaodai", 1, "sh"));
        User user = jdbcUser.fetchUser("daiwei");
        System.out.println(user);
    }
}
