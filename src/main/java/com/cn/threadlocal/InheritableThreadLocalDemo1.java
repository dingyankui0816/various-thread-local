package com.cn.threadlocal;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description inheritable-thread-local
 * InheritableThreadLocal 会将当前线程参数传递至子线程 （类似于值传递，子线程变更值不会变更父线程参数值）
 * 实现原理 ，子线程在初始化的时候会copy父线程的inheritableThreadLocals（用于存储当前线程的InheritableThreadLocal数组）
 * 结构 Entry[] table -> Entry(referent:InheritableThreadLocal,value:Object)
 * @Author: Levi.Ding
 * @Date: 2022/6/24 15:32
 * @Version V1.0
 */
public class InheritableThreadLocalDemo1 {
    static final InheritableThreadLocal<String> ITL1 = new InheritableThreadLocal<>();
    static final InheritableThreadLocal<String> ITL2 = new InheritableThreadLocal<>();
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(()->{
            Thread.currentThread().setName("Thread-1");
            ITL1.set(Thread.currentThread().getName());
            for (int i = 0; i < 10; i++) {
                System.out.println("线程：" +Thread.currentThread().getName()+ "中，第" + i + "调用，ITL1为:"+ITL1.get());
                if (i == 4){
                    ITL1.set(Thread.currentThread().getName() + "-" + i);
                }
            }
            ITL2.set(Thread.currentThread().getName());
            System.out.println("线程："+Thread.currentThread().getName() + "，设置ITL2值："+Thread.currentThread().getName());
            CountDownLatch countDownLatch = new CountDownLatch(1);
            new Thread(()->{
                Thread.currentThread().setName("Thread-1-Children-1");
                System.out.println("线程：" +Thread.currentThread().getName()+ "中，ITL2为:"+ITL2.get());
                ITL2.set(Thread.currentThread().getName());
                System.out.println("线程："+Thread.currentThread().getName() + "，设置ITL2值："+Thread.currentThread().getName());

                System.out.println("线程：" +Thread.currentThread().getName()+ "中，ITL2为:"+ITL2.get());

                System.out.println("线程：" +Thread.currentThread().getName()+ "中，ITL1为:"+ITL1.get());


                System.out.println("线程："+Thread.currentThread().getName() + "，设置ITL1值："+Thread.currentThread().getName());
                ITL1.set(Thread.currentThread().getName());
                System.out.println("线程：" +Thread.currentThread().getName()+ "中，ITL1为:"+ITL1.get());
                countDownLatch.countDown();
            }).start();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" +Thread.currentThread().getName()+ "中，ITL2为:"+ITL2.get());
            System.out.println("线程：" +Thread.currentThread().getName()+ "中，ITL1为:"+ITL1.get());
        });
        thread.start();
        System.in.read();
    }
}
