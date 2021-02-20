package io.daiwei.springbean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Daiwei on 2021/2/13
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private Integer age;

    private String addr;

    public static User newInstance() {
        return new User("daiwei", 25, "nb");
    }
}
