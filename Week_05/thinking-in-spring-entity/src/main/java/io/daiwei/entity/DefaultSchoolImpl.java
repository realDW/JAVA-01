package io.daiwei.entity;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.util.List;

/**
 * Created by Daiwei on 2021/2/18
 */
@ToString
@Data
@Log4j
public class DefaultSchoolImpl implements School {

    private String schoolName;

    private List<Klass> klassList;

    @Override
    public void ding() {
      log.info("class is beginning!");
        for (Klass klass : klassList) {
            klass.dong();
        }
    }
}
