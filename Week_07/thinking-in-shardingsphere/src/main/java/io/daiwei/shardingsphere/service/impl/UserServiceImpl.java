package io.daiwei.shardingsphere.service.impl;

import io.daiwei.shardingsphere.mapper.UserMapper;
import io.daiwei.shardingsphere.pojo.User;
import io.daiwei.shardingsphere.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Daiwei on 2021/3/8
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectByUser(Integer id) {
        return userMapper.selectUser(id);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
