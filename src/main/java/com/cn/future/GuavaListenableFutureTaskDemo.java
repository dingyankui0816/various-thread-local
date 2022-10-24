package com.cn.future;

import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description com.google.common.util.concurrent.ListenableFuture demo
 * @Author: Levi.Ding
 * @Date: 2022/8/17 10:16
 * @Version V1.0
 */
public class GuavaListenableFutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListenableFutureTask<String> listenableFutureTask = ListenableFutureTask.create(()->{

            TimeUnit.SECONDS.sleep(2);
            return "success : " + Thread.currentThread().getName();
        });
        listenableFutureTask.addListener(()->{
            String aaa = null;
            try {
                aaa = listenableFutureTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("调用成功 : " + Thread.currentThread().getName());
            System.out.println(aaa);
        }, Executors.newSingleThreadExecutor());
        Thread thread = new Thread(listenableFutureTask);
        thread.start();
        String s = listenableFutureTask.get();
        System.out.println(Thread.currentThread().getName() +" : " +s);

    }
}
