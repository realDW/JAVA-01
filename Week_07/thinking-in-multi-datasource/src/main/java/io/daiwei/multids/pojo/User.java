package io.daiwei.multids.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Daiwei on 2021/3/7
 */
@Data
@ToString
@NoArgsConstructor
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
