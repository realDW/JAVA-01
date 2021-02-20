package io.daiwei.springbean.factory;

import io.daiwei.springbean.entity.User;

/**
 * Created by Daiwei on 2021/2/13
 */
public class UserFactoryImpl implements UserFactory {

    @Override
    public User createUser() {
        return new User("daiwei", 22, "nc");
    }
}
