package io.daiwei.shardingsphere.service;

import io.daiwei.shardingsphere.pojo.User;

/**
 * Created by Daiwei on 2021/3/8
 */

public interface UserService {

    User selectByUser(Integer id);

    void insertUser(User user);
}
