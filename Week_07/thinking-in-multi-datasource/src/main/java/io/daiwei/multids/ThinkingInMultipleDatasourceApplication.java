package io.daiwei.multids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ThinkingInMultipleDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkingInMultipleDatasourceApplication.class, args);
    }

}
