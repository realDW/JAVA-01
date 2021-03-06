package io.daiwei.insert;

import io.daiwei.insert.jdbc.JdbcInsertTest;
import io.daiwei.jdbc.util.JdbcUtil;

/**
 * Created by Daiwei on 2021/3/4
 */
public class TestMain {

    public static void main(String[] args) {
        JdbcInsertTest test = new JdbcInsertTest();
//        test.insertByLoop();
//        test.insertSqlBatch();
//        test.insertByBatch(1000);
        test.insertConcurBatch();
    }

}
