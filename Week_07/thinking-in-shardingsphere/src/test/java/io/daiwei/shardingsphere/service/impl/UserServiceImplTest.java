package io.daiwei.shardingsphere.service.impl;

import io.daiwei.shardingsphere.pojo.User;
import io.daiwei.shardingsphere.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Daiwei on 2021/3/9
 */
@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void selectByUser() {
        User user = userService.selectByUser(1);
        System.out.println(user);
    }

    @Test
    void insertUser() {
        User user = new User("daiwei", 24, "shardingspherer");
        userService.insertUser(user);
    }
}