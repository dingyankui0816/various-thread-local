package com.cn.threadlocal;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import org.apache.dubbo.common.threadlocal.InternalThread;
import org.apache.dubbo.common.threadlocal.InternalThreadLocal;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description fast-thread-local
 * FastThreadLocal 不会将当前线程参数传递至子线程
 * @Author: Levi.Ding
 * @Date: 2022/6/24 16:43
 * @Version V1.0
 */
public class FastThreadLocalDemo1 {
    static final FastThreadLocal<String> ITL1 = new FastThreadLocal<>();
    static final FastThreadLocal<String> ITL2 = new FastThreadLocal<>();

    public static void main(String[] args) throws IOException {
        FastThreadLocalThread thread = new FastThreadLocalThread(() -> {
            Thread.currentThread().setName("Thread-1");
            ITL1.set(Thread.currentThread().getName());
            for (int i = 0; i < 10; i++) {
                System.out.println("线程：" + Thread.currentThread().getName() + "中，第" + i + "调用，ITL1为:" + ITL1.get());
                if (i == 4) {
                    ITL1.set(Thread.currentThread().getName() + "-" + i);
                }
            }
            ITL2.set(Thread.currentThread().getName());
            System.out.println("线程：" + Thread.currentThread().getName() + "，设置ITL2值：" + Thread.currentThread().getName());
            CountDownLatch countDownLatch = new CountDownLatch(1);
            new FastThreadLocalThread(() -> {
                Thread.currentThread().setName("Thread-1-Children-1");
                System.out.println("线程：" + Thread.currentThread().getName() + "中，ITL2为:" + ITL2.get());
                ITL2.set(Thread.currentThread().getName());
                System.out.println("线程：" + Thread.currentThread().getName() + "，设置ITL2值：" + Thread.currentThread().getName());

                System.out.println("线程：" + Thread.currentThread().getName() + "中，ITL2为:" + ITL2.get());

                System.out.println("线程：" + Thread.currentThread().getName() + "中，ITL1为:" + ITL1.get());


                System.out.println("线程：" + Thread.currentThread().getName() + "，设置ITL1值：" + Thread.currentThread().getName());
                ITL1.set(Thread.currentThread().getName());
                System.out.println("线程：" + Thread.currentThread().getName() + "中，ITL1为:" + ITL1.get());
                countDownLatch.countDown();
            }).start();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "中，ITL2为:" + ITL2.get());
            System.out.println("线程：" + Thread.currentThread().getName() + "中，ITL1为:" + ITL1.get());
        });
        thread.start();
        System.in.read();
    }
}
