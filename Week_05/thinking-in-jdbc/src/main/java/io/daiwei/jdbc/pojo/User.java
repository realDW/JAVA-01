package io.daiwei.jdbc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Daiwei on 2021/2/20
 */
@ToString
@Data
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private Integer age;

    private String addr;

    public User(String username, Integer age, String addr) {
        this.username = username;
        this.age = age;
        this.addr = addr;
    }
}
