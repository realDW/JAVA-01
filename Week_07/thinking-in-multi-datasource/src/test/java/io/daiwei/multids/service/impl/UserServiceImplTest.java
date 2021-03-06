package io.daiwei.multids.service.impl;

import io.daiwei.multids.pojo.User;
import io.daiwei.multids.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Daiwei on 2021/3/7
 */
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void selectUser() {
        User user = userService.selectUser(1);
        System.out.println(user.toString());
    }

    @Test
    void insert() {
        User user = new User("daiwei", 1, "from mybatis");
        userService.insert(user);
    }
}