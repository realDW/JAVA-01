package io.daiwei.springbean;

import io.daiwei.springbean.entity.User;
import io.daiwei.springbean.factory.UserFactory;
import io.daiwei.springbean.factory.UserFactoryImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jws.soap.SOAPBinding;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Spring 实例构造方法
 * Created by Daiwei on 2021/2/13
 */
@Log4j
public class SpringBeanMain {

    public static void main(String[] args) {

        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-context.xml");

        log.info("构造器注入");
        User bean = beanFactory.getBean("user-by-constructor", User.class);
        log.info(bean);

        log.info("属性注入");
        User userFromProps = beanFactory.getBean("user-by-props", User.class);
        log.info(userFromProps);

        log.info("静态工厂方法");
        User fromStaticMethod = beanFactory.getBean("user-by-static-method", User.class);
        log.info(fromStaticMethod);

        log.info("工厂方法bean");
        User fromFactory = beanFactory.getBean("user-by-factory", User.class);
        log.info(fromFactory);

        log.info("service 加载");
        User user = loadFromServices();
        log.info(user);

        log.info("capableFactory 方式");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-context.xml");
        AutowireCapableBeanFactory capableBeanFactory = context.getAutowireCapableBeanFactory();
        UserFactoryImpl fromCapableFactory = capableBeanFactory.createBean(UserFactoryImpl.class);
        log.info(fromCapableFactory.createUser());

        log.info("annotation 注册bean");
        User fromAnnotation = beanFactory.getBean("user-by-annotation", User.class);
        log.info(fromAnnotation);

    }

    private static User loadFromServices() {
        ServiceLoader<UserFactory> load = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        Iterator<UserFactory> iterator = load.iterator();
        User user = null;
        if (iterator.hasNext()) {
            user = iterator.next().createUser();
        }
        return user;
    }
}
