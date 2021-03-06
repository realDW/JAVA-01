package io.daiwei.multids.aop;

import io.daiwei.multids.annotation.DS;
import io.daiwei.multids.configuration.DataSourceHolder;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Daiwei on 2021/3/7
 */
@Aspect
@Component
@Slf4j
public class DSAdvice {

    @Before(" @annotation(ds)")
    public void changeDataSource(JoinPoint point, DS ds) {
        String dsName = DataSourceHolder.getMaster();
        if (StringUtils.hasText(ds.db()) && DataSourceHolder.containDataSource(ds.db())) {
            dsName = ds.db();
            DataSourceHolder.setDataSource(ds.db());
        }
        log.info("use dataSource : {}", dsName);
    }

    @After("@annotation(ds)")
    public void remove(JoinPoint point, DS ds) {
        log.info("remove dataSource[{}]", StringUtils.hasText(ds.db()) ? ds.db()
                : DataSourceHolder.getDataSource());
        DataSourceHolder.removeDataSource();
    }



}
