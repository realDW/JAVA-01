package io.daiwei.proxy;

import io.daiwei.proxy.common.User;
import io.daiwei.proxy.common.UserImpl;
import io.daiwei.proxy.cglib.UserCglibProxy;
import lombok.extern.log4j.Log4j;

/**
 * Created by Daiwei on 2021/2/12
 */
@Log4j
public class UserProxyMain {

    public static void main(String[] args) {
//        User user = new UserImpl();
//        user.hello("daiwei");

//        User user = new UserImpl();
//        InvocationHandler handler = new UserJDKProxy(user);
//        User userProxy = (User) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), user.getClass().getInterfaces(), handler);
//        InvocationHandler invocationHandler = Proxy.getInvocationHandler(userProxy);
//        userProxy.hello("daiwei");

        UserCglibProxy cglibProxy = new UserCglibProxy();
        UserImpl user = cglibProxy.newInstance(UserImpl.class);
        user.hello("daiwei");
    }
}
