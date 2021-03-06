package io.daiwei.multids.configuration;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Daiwei on 2021/3/6
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    private static final List<String> dsList = new ArrayList<>();

    private static final AtomicInteger cnt = new AtomicInteger();

    @Getter
    private static String master;

    public static void setDataSource(String name) {
        holder.set(name);
    }

    public static String getDataSource() {
        String dsName = holder.get();
        if (StringUtils.hasText(dsName) && dsList.size() >= 2
                && !dsName.equals(master)) {
            return salveRobin();
        }
        return master;
    }

    public static void removeDataSource() {
        holder.remove();
    }

    public static void addDataSource(String name) {
        if (!StringUtils.hasText(master)) {
            master = name;
        }
        dsList.add(name);
    }

    public static boolean containDataSource(String name) {
        return dsList.contains(name);
    }

    private static String salveRobin() {
        int idx = cnt.getAndIncrement() % (dsList.size() - 1);
        List<String> names = dsList.stream().skip(1).collect(Collectors.toList());
        return names.get(idx);
    }
}
