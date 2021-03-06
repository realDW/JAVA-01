package io.daiwei.insert.jdbc;

import io.daiwei.jdbc.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.*;

/**
 * Created by Daiwei on 2021/3/4
 */
public class JdbcInsertTest {

    /**
     * 1000 24s
     */
    public void insertByLoop() {
        long start = System.currentTimeMillis();
        Connection conn = JdbcUtil.getConnFromHikari();
        PreparedStatement stat = null;
        try {
            String sql = "insert into tb_order_test(good_id, user_id, good_status) values (?, ?, ?)";
            conn.setAutoCommit(false);
            stat = conn.prepareStatement(sql);
            for (int i = 0; i < 3000; i++) {
                stat.setLong(1, i);
                stat.setLong(2, i);
                stat.setInt(3, 1);
                stat.execute();
            }
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public void insertSqlBatch() {
        long start = System.currentTimeMillis();
        Connection conn = JdbcUtil.getConnFromHikari();
        PreparedStatement stat = null;
        try {
            String sql = "insert into tb_order_test(good_id, user_id, good_status) values (?, ?, ?)";
            conn.setAutoCommit(false);
            stat = conn.prepareStatement(sql);
            for (int i = 0; i < 1000000; i++) {
                stat.setLong(1, i);
                stat.setLong(2, i);
                stat.setInt(3, 1);
                stat.addBatch();
            }
            stat.executeBatch();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 100w 5302ms
     */
    public void insertByBatch(int n) {
        Connection conn = JdbcUtil.getConnFromHikari();
        Statement stat = null;
//        long start = System.currentTimeMillis();
        try {
            stat = conn.createStatement();
            String sql;
            StringBuilder sb = new StringBuilder("insert into tb_order_test_1(good_id, user_id, good_status, user_name, addr) values");
            conn.setAutoCommit(false);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 1000; j++) {
                    sb.append("(" + i +" , " + j +", 1, 'daiwei', 'Shanghai'),");
                }
                sql = sb.deleteCharAt(sb.length() - 1).toString();
                stat.addBatch(sql);
                sb.delete(82, sb.length());
            }
            stat.executeLargeBatch();
            conn.commit();
//            System.out.println(System.currentTimeMillis() - start);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, stat, null);
        }
    }


    /**
     * 100w 4655 ms
     */
    public void insertConcurBatch() {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println(System.currentTimeMillis() - start);
            executor.shutdown();
        });
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                insertByBatch(250);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void insertByRewriteBatch() {
        Connection conn = JdbcUtil.getConnFromHikari();
        long start = System.currentTimeMillis();
        Statement stat = null;
        try {
            stat = conn.createStatement();
            String sql;
            conn.setAutoCommit(false);
            for (int i = 0; i < 1000000; i++) {
                sql = "insert into tb_order_test(good_id, user_id, good_status) values (" + i +" , " + i +", 1)";
                stat.addBatch(sql);
            }
            stat.executeBatch();
            conn.commit();
            System.out.println(System.currentTimeMillis() - start);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.release(conn, stat, null);
        }
    }

}
