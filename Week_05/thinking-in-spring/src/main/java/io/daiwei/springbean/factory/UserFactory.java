package io.daiwei.springbean.factory;

import io.daiwei.springbean.entity.User;

/**
 * Created by Daiwei on 2021/2/13
 */
public interface UserFactory {

    default User createUser() {
        return User.newInstance();
    }
}
