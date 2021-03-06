package io.daiwei.multids.configuration.properties;

import lombok.Data;

/**
 * Created by Daiwei on 2021/3/6
 */
@Data
public class DataSourceProp {

    private String driver;

    private String url;

    private String username;

    private String password;
}
