package io.daiwei.multids.mapper;

import io.daiwei.multids.pojo.User;

/**
 * Created by Daiwei on 2021/3/7
 */
public interface UserMapper {

    User selectUser(Integer id);

    void insertUser(User user);
}
