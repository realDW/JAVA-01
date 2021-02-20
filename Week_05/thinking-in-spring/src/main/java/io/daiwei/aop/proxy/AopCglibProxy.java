package io.daiwei.aop.proxy;

import io.daiwei.aop.annotation.AopAfter;
import io.daiwei.aop.annotation.AopAround;
import io.daiwei.aop.annotation.AopBefore;
import lombok.extern.log4j.Log4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Daiwei on 2021/2/13
 */
@Log4j
public class AopCglibProxy implements MethodInterceptor {

    private static final String METHOD_SPLIT = "#";

    private final Enhancer enhancer;

    public AopCglibProxy() {
        this.enhancer = new Enhancer();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        handleBefore(method, args);
        Object res = methodProxy.invokeSuper(o, args);
        handleAfter(method, args);
        return res;
    }

    /**
     * 创建实例对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T newProxyInstance(Class<T> clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return clazz.cast(enhancer.create());
    }

    /**
     * 前置处理
     * @param method
     * @param objects
     */
    private void handleBefore(Method method, Object[] objects) {
        AopBefore before = method.getAnnotation(AopBefore.class);
        AopAround around = method.getAnnotation(AopAround.class);
        if (before != null) {
            reflectRevoke(before.exeMethod());
        }
        if (around != null) {
            reflectRevoke(around.beforeMethod());
        }
    }

    /**
     * 后置处理
     * @param method
     * @param objects
     */
    private void handleAfter(Method method, Object[] objects) {
        AopAfter after = method.getAnnotation(AopAfter.class);
        AopAround around = method.getAnnotation(AopAround.class);
        if (after != null) {
            reflectRevoke(after.exeMethod());
        }
        if (around != null) {
            reflectRevoke(around.afterMethod());
        }
    }
    
    private void reflectRevoke(String exeMethod) {
        if (exeMethod == null || exeMethod.length() == 0) {
            return;
        }
        String[] packageAndMethod = exeMethod.split(METHOD_SPLIT);
        String packStr = packageAndMethod[0];
        String methodStr = packageAndMethod[1];
        try {
            Class<?> clazz = Class.forName(packStr);
            Method invokeMethod = clazz.getMethod(methodStr);
            invokeMethod.invoke(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            log.error("class["+ packStr +"] not find!", e);
        } catch (NoSuchMethodException e) {
            log.error("method["+ methodStr +"] not find!", e);
        } catch (IllegalAccessException e) {
            log.error("plz set invoke method public", e);
        } catch (InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
