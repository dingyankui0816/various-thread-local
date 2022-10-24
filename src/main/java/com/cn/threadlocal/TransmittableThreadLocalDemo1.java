package com.cn.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description transmittable-thread-local
 * TransmittableThreadLocal 会将当前线程参数传递至子线程 （类似于值传递，子线程变更值不会变更父线程参数值）
 * 可以通过 TransmittableThreadLocal.Transmitter 管理所有生成的 TransmittableThreadLocal 实例
 * 可以将 ThreadLocal 注册至 TransmittableThreadLocal.Transmitter 进行管理
 *
 * JDK的InheritableThreadLocal类可以完成父线程到子线程的值传递。
 * 但对于使用线程池等会池化复用线程的执行组件的情况，线程由线程池创建好，并且线程是池化起来反复使用的；
 * 这时父子线程关系的ThreadLocal值传递已经没有意义，应用需要的实际上是把 任务提交给线程池时的ThreadLocal值传递到 任务执行时。
 * https://github.com/alibaba/transmittable-thread-local#-%E5%8A%9F%E8%83%BD
 *
 * @Author: Levi.Ding
 * @Date: 2022/6/27 13:58
 * @Version V1.0
 */
public class TransmittableThreadLocalDemo1 {
    static final TransmittableThreadLocal<String> ITL1 = new TransmittableThreadLocal<>();
    static final TransmittableThreadLocal<String> ITL2 = new TransmittableThreadLocal<>();
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

            Object capture = TransmittableThreadLocal.Transmitter.capture();
            System.out.println(capture);
        });
        thread.start();
        System.in.read();
    }
}
