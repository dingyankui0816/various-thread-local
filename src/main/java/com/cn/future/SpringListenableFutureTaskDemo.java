package com.cn.future;

import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description org.springframework.util.concurrent.listenableFutureTask
 * @Author: Levi.Ding
 * @Date: 2022/8/17 15:17
 * @Version V1.0
 */
public class SpringListenableFutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListenableFutureTask<String> listenableFutureTask = new ListenableFutureTask(()->{

            TimeUnit.SECONDS.sleep(2);
            return "success : " + Thread.currentThread().getName();
        });

        listenableFutureTask.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {

                System.out.println("调用失败 : " + Thread.currentThread().getName());
            }

            @Override
            public void onSuccess(String o) {
                System.out.println("调用成功 : " + Thread.currentThread().getName());
            }
        });

        Thread thread = new Thread(listenableFutureTask);
        thread.start();
        String s = listenableFutureTask.get();

        System.out.println(Thread.currentThread().getName() +" : " +s);

    }
}
