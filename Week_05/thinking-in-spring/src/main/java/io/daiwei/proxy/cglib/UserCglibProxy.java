package io.daiwei.proxy.cglib;

import lombok.extern.log4j.Log4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Daiwei on 2021/2/12
 */
@Log4j
public class UserCglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    /**
     * @param o 被代理的对象
     * @param method 代理的方法
     * @param objects 方法的参数
     * @param methodProxy CGLIB方法代理对象
     * @return cglib生成用来代替Method对象的一个对象，使用MethodProxy比调用JDK自身的Method直接执行方法效率会有提升
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("cglib begin...");
        Object res = methodProxy.invokeSuper(o, objects);
        log.info("cglib end...");
        return res;
    }

    public <T> T newInstance(Class<T> clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return clazz.cast(enhancer.create());
    }

}
