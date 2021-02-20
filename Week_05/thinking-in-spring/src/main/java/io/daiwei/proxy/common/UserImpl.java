package io.daiwei.proxy.common;

import io.daiwei.aop.annotation.AopBefore;
import io.daiwei.proxy.common.User;
import lombok.extern.log4j.Log4j;

/**
 * Created by Daiwei on 2021/2/12
 */
@Log4j
public class UserImpl implements User {

    @Override
    @AopBefore(exeMethod = "io.daiwei.aop.advice.AdviceMethod#helloBefore")
    public void hello(String name) {
        System.out.println("hello ".concat(name));
    }


}
