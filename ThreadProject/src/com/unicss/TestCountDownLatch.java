package com.unicss;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。

主要方法

 public CountDownLatch(int count);

 public void countDown();

 public void await() throws InterruptedException
 

构造方法参数指定了计数的次数

countDown方法，当前线程调用此方法，则计数减一

await方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
 * 用例讲解参考  https://blog.csdn.net/u013136708/article/details/49444459
 */


public class TestCountDownLatch {
public static void main(String[] args) throws InterruptedException {
   // 开始的倒数锁
   final CountDownLatch begin = new CountDownLatch(1);
   // 结束的倒数锁
   final CountDownLatch end = new CountDownLatch(10);
   // 十名选手
   final ExecutorService exec = Executors.newFixedThreadPool(10);
  
   for (int index = 0; index < 10; index++) {
    final int NO = index + 1;
    Runnable run = new Runnable() {
     public void run() {
      try {
       begin.await();//一直阻塞
       Thread.sleep((long) (Math.random() * 10000));
       System.out.println("No." + NO + " arrived");
      } catch (InterruptedException e) {
      } finally {
       end.countDown();
      }
     }
    };
    exec.submit(run);
   }
   System.out.println("Game Start");
   begin.countDown();
   end.await();
   System.out.println("Game Over");
   exec.shutdown();
}
}