package io.daiwei.shardingsphere.mapper;

import io.daiwei.shardingsphere.pojo.User;

/**
 * Created by Daiwei on 2021/3/8
 */
public interface UserMapper {

    User selectUser(Integer id);

    void insertUser(User user);
}
