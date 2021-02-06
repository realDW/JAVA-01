package daiwei.geekbang.homework.common;

/**
 * 手工构建一个 TaskResult
 * Created by Daiwei on 2021/2/1
 */
public class TaskResult {

    private volatile Integer res;

    public void setRes(Integer res) {
        this.res = res;
    }

    public Integer getRes() {
        return this.res;
    }

    public boolean isDone() {
        return this.res != null;
    }
}
