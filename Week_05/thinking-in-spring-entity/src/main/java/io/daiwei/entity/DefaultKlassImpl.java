package io.daiwei.entity;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

/**
 * Created by Daiwei on 2021/2/14
 */
@Data
@ToString
@Log4j
public class DefaultKlassImpl implements Klass {

    private Integer grade;

    private String klassNo;

    private Student monitor;

    @Override
    public void dong() {
        log.info("this is grade["+ grade +"] class["+ klassNo +"] and monitor is " + monitor.toString());
    }
}
