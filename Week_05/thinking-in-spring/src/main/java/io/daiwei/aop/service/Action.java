package io.daiwei.aop.service;

import io.daiwei.aop.annotation.AopAfter;
import io.daiwei.aop.annotation.AopAround;
import io.daiwei.aop.annotation.AopBefore;
import lombok.extern.log4j.Log4j;

/**
 * Created by Daiwei on 2021/2/13
 */
@Log4j
public class Action {

    @AopBefore(exeMethod = "io.daiwei.aop.advice.AdviceMethod#helloBefore")
    @AopAfter(exeMethod = "io.daiwei.aop.advice.AdviceMethod#helloAfter")
    public String sayName(String name) {
        String str = "this is my friend, " + name + ".";
        log.info(str);
        return str;
    }

    @AopAround(beforeMethod = "io.daiwei.aop.advice.AdviceMethod#helloBefore",
            afterMethod = "io.daiwei.aop.advice.AdviceMethod#helloAfter")
    public String sayHello(String name) {
        String str =  "hello " + name;
        log.info(str);
        return str;
    }
}
