package com.cn.future;

import com.google.common.util.concurrent.SettableFuture;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Description com.google.common.util.concurrent.SettableFuture
 * @Author: Levi.Ding
 * @Date: 2022/8/17 15:34
 * @Version V1.0
 */
public class GuavaSettableFutureDemo {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        SettableFuture<String> settableFuture = SettableFuture.create();
        settableFuture.addListener(() -> {
            try {
                System.out.println("调用成功 : " + Thread.currentThread().getName() + settableFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });

//        settableFuture.setException(new RuntimeException());
        // 触发回调
        settableFuture.set("bbb");
        System.out.println(settableFuture.get());
        System.in.read();
    }
}
