package com.cn.juc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Description J.U.C map demo
 * @Author: Levi.Ding
 * @Date: 2023/6/7 15:40
 * @Version V1.0
 */
public class MapDemo {

    /**
     * 底层存储结构，数组+链表
     *
     * 优势: 操作数据 当未发生Hash碰撞时，速度为 O(1).当发生Hash碰撞时，速度基于当前 hash碰撞时的链表长度决定，未超过阈值时 O(n)，超过阈值 O(logn)
     * 缺点: 由于Hash碰撞的原因，所以在并发量过多时，同时Hash值碰撞发生几率变高，对存储会造成影响。同时key不是顺序存储
     *
     */
    static ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();


    /**
     * 底层存储结构，跳表
     *
     * 优势: key是顺序存储。并发量不会对存储造成太大影响
     * 缺点: 操作数据速度平均为 O(logn)
     *
     */
    static ConcurrentSkipListMap<Integer,String> concurrentSkipListMap = new ConcurrentSkipListMap<>();



    public static void main(String[] args) {
        concurrentHashMap.put("a","a");


        concurrentSkipListMap.put(1,"a");
        concurrentSkipListMap.put(3,"a");
        concurrentSkipListMap.put(8,"a");
        concurrentSkipListMap.put(3,"a");

    }

    /**
     * @Description: Java 标签用法
     * <a href="https://blog.csdn.net/zhaoheng2017/article/details/78385973" />
     * @author Levi.Ding
     * @date 2023/6/7 16:27
     * @return : void
     */
    public static void breakLabel() {
        //在外层循环处添加outer标签
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //设置outer的判断条件
                if (i == 1) {
                    break outer;
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }

}
