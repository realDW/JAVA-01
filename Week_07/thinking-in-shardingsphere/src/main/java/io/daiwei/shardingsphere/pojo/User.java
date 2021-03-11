package io.daiwei.shardingsphere.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Daiwei on 2021/3/8
 */
@Data
@ToString
@AllArgsConstructor
public class User {

    private Integer id;

    private String username;

    private Integer gender;

    private String addr;

    public User(String username, Integer gender, String addr) {
        this.username = username;
        this.gender = gender;
        this.addr = addr;
    }
}
