package com.cn.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.io.IOException;
import java.util.concurrent.*;

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
public class TransmittableThreadLocalDemo2 {
    static final TransmittableThreadLocal<String> ITL1 = new TransmittableThreadLocal<>();
    static final TransmittableThreadLocal<String> ITL2 = new TransmittableThreadLocal<>();
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //普通线程池
        System.out.println("=================== 普通线程池 ===================");
        ITL1.set("main-thread");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 8,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());

        Future<?> submit1 = threadPoolExecutor.submit(() -> {
            String mainThread = ITL1.get();
            System.out.println(Thread.currentThread().getName() + ":" + mainThread);
        });
        submit1.get();

        ITL1.set("main-thread-edit");
        Future<?> submit2 = threadPoolExecutor.submit(() -> {
            String mainThread = ITL1.get();
            System.out.println(Thread.currentThread().getName() + ":" + mainThread);
        });
        submit2.get();

        System.out.println("=================== END ===================");


        System.out.println("=================== TTL线程池 ===================");

        ITL2.set("main-thread-ttl");

        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(1, 8,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()));

        Future<?> submitTtl1 = ttlExecutorService.submit(() -> {
            String mainThread = ITL2.get();
            System.out.println(Thread.currentThread().getName() + ":" + mainThread);
        });
        submitTtl1.get();

        ITL2.set("main-thread-ttl-edit");
        Future<?> submitTtl2 = ttlExecutorService.submit(() -> {
            String mainThread = ITL2.get();
            System.out.println(Thread.currentThread().getName() + ":" + mainThread);
        });
        submitTtl2.get();

        System.out.println("=================== END ===================");


        System.in.read();
    }
}
