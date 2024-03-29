package com.cn.future;

import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @Description org.springframework.util.concurrent.SettableListenableFuture
 * @Author: Levi.Ding
 * @Date: 2022/8/17 16:14
 * @Version V1.0
 */
public class SpringSettableListenableFutureDemo {

    public static void main(String[] args) throws IOException {
        SettableListenableFuture<String> settableListenableFuture = new SettableListenableFuture<>();

        settableListenableFuture.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {

                System.out.println("调用失败 : " + Thread.currentThread().getName());
            }

            @Override
            public void onSuccess(String o) {
                System.out.println("调用成功 : " + Thread.currentThread().getName()+"aaa");
            }
        });
        Thread thread = new Thread(()->{
            //触发回调
            settableListenableFuture.set("aaa");
            try {
                System.out.println(settableListenableFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        new Thread(()->{
            //触发回调
            settableListenableFuture.set("bbb");
            try {
                System.out.println(settableListenableFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();

        System.in.read();
    }

}
