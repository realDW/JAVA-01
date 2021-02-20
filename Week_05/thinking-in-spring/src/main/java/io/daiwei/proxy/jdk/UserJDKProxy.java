package io.daiwei.proxy.jdk;

import io.daiwei.proxy.common.User;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Daiwei on 2021/2/12
 */
@Log4j
public class UserJDKProxy implements InvocationHandler {

    private User user;

    public UserJDKProxy(User user) {
        this.user = user;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin~");
        Object invokeRes = method.invoke(this.user, args);
        log.info("end~");
        return invokeRes;
    }
}
