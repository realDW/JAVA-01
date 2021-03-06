package io.daiwei.proxy.datasource;

/**
 * Created by Daiwei on 2021/3/6
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> dsName = new ThreadLocal<>();

    public static String getDsName(String defaultName) {
        return dsName.get() != null && dsName.get().length() != 0 ? dsName.get() : defaultName;
    }

    public static void setDsName(String name) {
        dsName.set(name);
    }

    public static void remove() {
        dsName.remove();
    }
}
