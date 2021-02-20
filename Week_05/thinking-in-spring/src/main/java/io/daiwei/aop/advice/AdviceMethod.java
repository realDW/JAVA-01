package io.daiwei.aop.advice;

import lombok.extern.log4j.Log4j;

/**
 * Created by Daiwei on 2021/2/13
 */
@Log4j
public class AdviceMethod {

    public void helloBefore() {
        log.info("hello before!!");
    }

    public void helloAfter() {
        log.info("hello after!!");
    }
}
