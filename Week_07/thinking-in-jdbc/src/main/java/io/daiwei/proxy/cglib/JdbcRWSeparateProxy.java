package io.daiwei.proxy.cglib;

import io.daiwei.proxy.annotation.JdbcDS;
import io.daiwei.proxy.datasource.DataSourceHolder;
import io.daiwei.proxy.datasource.DataSourcesManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * jdbc 前面加一层代理实现动态切换数据源
 * Created by Daiwei on 2021/3/6
 */
public class JdbcRWSeparateProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        pickDataSourceName(method);
        return methodProxy.invokeSuper(o, objects);
    }

    public <T> T getInstance(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return clazz.cast(enhancer.create());
    }

    private boolean isReadMethod(String method) {
        return method.startsWith("select") || method.startsWith("list")
                || method.startsWith("get");
    }

    private void pickDataSourceName(Method method) {
        JdbcDS jdbcDS = method.getAnnotation(JdbcDS.class);
        if (jdbcDS != null) {
            DataSourceHolder.setDsName(jdbcDS.db());
        } else {
            DataSourceHolder.setDsName(isReadMethod(method.getName()) ? DataSourcesManager.getInstance().getRobinSlaveName()
                    : DataSourcesManager.getInstance().masterName);
        }
    }
}
