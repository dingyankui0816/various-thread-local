package com.cn.future;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @Description future task demo
 * @Author: Levi.Ding
 * @Date: 2022/8/16 18:00
 * @Version V1.0
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        FutureTask<String> futureTask = new FutureTask<>(()->{
            TimeUnit.SECONDS.sleep(2);
            return "success";
        });
        Thread thread = new Thread(futureTask);
        thread.start();

//        Executors.newCachedThreadPool().submit()
        String s = futureTask.get();
        System.out.println(s);
    }
}
