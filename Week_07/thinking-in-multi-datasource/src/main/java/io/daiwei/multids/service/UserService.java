package io.daiwei.multids.service;

import io.daiwei.multids.pojo.User;

/**
 * Created by Daiwei on 2021/3/7
 */
public interface UserService {

    void insert(User user);

    User selectUser(Integer id);
}
