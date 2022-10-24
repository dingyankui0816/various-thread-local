package com.cn.threadlocal;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description thread-local-demo
 * ThreadLocal 不会将当前线程参数传递至子线程
 * @Author: Levi.Ding
 * @Date: 2022/6/24 14:09
 * @Version V1.0
 */
public class ThreadLocalDemo1 {
    public static final ThreadLocal<String> TL1 = new ThreadLocal<>();
    public static final ThreadLocal<String> TL2 = new ThreadLocal<>();

    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(()->{
            Thread.currentThread().setName("Thread-1");
            TL1.set(Thread.currentThread().getName());
            for (int i = 0; i < 10; i++) {
                System.out.println("线程：" +Thread.currentThread().getName()+ "中，第" + i + "调用，TL1为:"+TL1.get());
                if (i == 4){
                    TL1.set(Thread.currentThread().getName() + "-" + i);
                }
            }
            TL2.set(Thread.currentThread().getName());
            System.out.println("线程："+Thread.currentThread().getName() + "，设置TL2值："+Thread.currentThread().getName());
            CountDownLatch countDownLatch = new CountDownLatch(1);
            new Thread(()->{
                Thread.currentThread().setName("Thread-1-Children-1");
                System.out.println("线程：" +Thread.currentThread().getName()+ "中，TL2为:"+TL2.get());
                TL2.set(Thread.currentThread().getName());
                System.out.println("线程："+Thread.currentThread().getName() + "，设置TL2值："+Thread.currentThread().getName());

                System.out.println("线程：" +Thread.currentThread().getName()+ "中，TL2为:"+TL2.get());

                System.out.println("线程：" +Thread.currentThread().getName()+ "中，TL1为:"+TL1.get());


                System.out.println("线程："+Thread.currentThread().getName() + "，设置TL1值："+Thread.currentThread().getName());
                TL1.set(Thread.currentThread().getName());
                System.out.println("线程：" +Thread.currentThread().getName()+ "中，TL1为:"+TL1.get());
                countDownLatch.countDown();
            }).start();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" +Thread.currentThread().getName()+ "中，TL2为:"+TL2.get());
            System.out.println("线程：" +Thread.currentThread().getName()+ "中，TL1为:"+TL1.get());
        });
        thread.start();
        System.in.read();
    }
}
