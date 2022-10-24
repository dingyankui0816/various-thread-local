package com.cn.threadlocal;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description thread-local-demo
 * @Author: Levi.Ding
 * @Date: 2022/6/24 14:09
 * @Version V1.0
 */
public class ThreadLocalDemo2 {

    public static final ThreadLocal<String>[] threadLocals  = new ThreadLocal[17];
    static {
        for (int i = 0; i < 17; i++) {
            threadLocals[i] = new ThreadLocal<>();
        }
    }


    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(()->{
            Thread.currentThread().setName("Thread-1");
            for (int i = 0; i < threadLocals.length; i++) {
                if (i == 16){
                    threadLocals[i].set(Thread.currentThread().getName());
                }
                threadLocals[i].set(Thread.currentThread().getName());
            }
        });
        thread.start();
        System.in.read();
    }
}
