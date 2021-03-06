package io.daiwei.multids.service.impl;

import io.daiwei.multids.annotation.DS;
import io.daiwei.multids.mapper.UserMapper;
import io.daiwei.multids.pojo.User;
import io.daiwei.multids.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Daiwei on 2021/3/7
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @DS
    public void insert(User user) {
        userMapper.insertUser(user);
    }

    @Override
    @DS(db = "slave")
    public User selectUser(Integer id) {
        User user = userMapper.selectUser(id);
        return user;
    }
}
