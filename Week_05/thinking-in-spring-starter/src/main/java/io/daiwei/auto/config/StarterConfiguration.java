package io.daiwei.auto.config;

import io.daiwei.auto.condition.AutoCondition;
import io.daiwei.entity.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by Daiwei on 2021/2/19
 */
@Configuration
@Conditional(AutoCondition.class)
public class StarterConfiguration {


    @Bean
    @DependsOn({"student1", "klass1"})
    public School School() {
        DefaultSchoolImpl defaultSchool = new DefaultSchoolImpl();
        defaultSchool.setSchoolName("hope school");
        defaultSchool.setKlassList(Arrays.asList(klass1()));
        return defaultSchool;
    }

    @Bean
    public Student student1() {
        Student student = new Student();
        student.setStuId(1L);
        student.setStuAddr("hello road no. 1886 ");
        student.setStuName("helloStarter");
        return student;
    }

    @Bean
    @DependsOn("student1")
    public Klass klass1() {
        DefaultKlassImpl klass = new DefaultKlassImpl();
        klass.setKlassNo("klass1");
        klass.setGrade(1);
        klass.setMonitor(student1());
        return klass;
    }

}
