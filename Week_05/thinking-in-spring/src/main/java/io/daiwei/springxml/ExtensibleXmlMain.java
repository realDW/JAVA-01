package io.daiwei.springxml;

import io.daiwei.entity.School;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Arrays;

/**
 * Created by Daiwei on 2021/2/14
 */
@Log4j
public class ExtensibleXmlMain {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        reader.loadBeanDefinitions("META-INF/extensible-xml-context.xml");

        String[] names = beanFactory.getBeanDefinitionNames();
        log.info("beans in context -" + Arrays.toString(names));

        School school = beanFactory.getBean("game-of-thrones", School.class);

        school.ding();
    }
}
