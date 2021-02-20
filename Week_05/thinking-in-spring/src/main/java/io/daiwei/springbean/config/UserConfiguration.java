package io.daiwei.springbean.config;

import io.daiwei.springbean.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Daiwei on 2021/2/13
 */
@Configuration
public class UserConfiguration {

    @Bean("user-by-annotation")
    public User userFromAnnotation() {
        return new User("daiwei", 6, "wh");
    }
}
