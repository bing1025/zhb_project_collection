package com.unicss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*http://www.cnblogs.com/liuling/p/2013-8-21-01.html
 * CyclicBarrier 循环栅栏
它有两层含义，一个是栅栏，一个是循环。先看栅栏，意思就是想一堵墙一样，可以同时对多个线程状态进行管理。

CyclicBarrier是一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。

　　CyclicBarrier类似于CountDownLatch也是个计数器， 不同的是CyclicBarrier数的是调用了CyclicBarrier.await()进入等待的线程数， 当线程数达到了CyclicBarrier初始时规定的数目时，所有进入等待状态的线程被唤醒并继续。 CyclicBarrier就象它名字的意思一样，可看成是个障碍， 所有的线程必须到齐后才能一起通过这个障碍。 CyclicBarrier初始时还可带一个Runnable的参数，此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行。

构造方法摘要
CyclicBarrier(int parties) 
          创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，但它不会在每个 barrier 上执行预定义的操作。
CyclicBarrier(int parties, Runnable barrierAction) 
          创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，并在启动 barrier 时执行给定的屏障操作，该操作由最后一个进入 barrier 的线程执行

方法摘要
int	await() 
          在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。


 */

public class TestCyclicBarrier {
  // 徒步需要的时间: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan
  private static int[] timeWalk = { 5, 8, 15, 15, 10 };
  // 自驾游
  private static int[] timeSelf = { 1, 3, 4, 4, 5 };
  // 旅游大巴
  private static int[] timeBus = { 2, 4, 6, 6, 7 };
  
  static String now() {
     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
     return sdf.format(new Date()) + ": ";
  }
  static class Tour implements Runnable {
     private int[] times;
     private CyclicBarrier barrier;
     private String tourName;
     public Tour(CyclicBarrier barrier, String tourName, int[] times) {
       this.times = times;
       this.tourName = tourName;
       this.barrier = barrier;
     }
     public void run() {
       try {
         Thread.sleep(times[0] * 1000);
         System.out.println(now() + tourName + " Reached Shenzhen");
         barrier.await();
         Thread.sleep(times[1] * 1000);
         System.out.println(now() + tourName + " Reached Guangzhou");
         barrier.await();
         Thread.sleep(times[2] * 1000);
         System.out.println(now() + tourName + " Reached Shaoguan");
         barrier.await();
         Thread.sleep(times[3] * 1000);
         System.out.println(now() + tourName + " Reached Changsha");
         barrier.await();
         Thread.sleep(times[4] * 1000);
         System.out.println(now() + tourName + " Reached Wuhan");
         barrier.await();
       } catch (InterruptedException e) {
       } catch (BrokenBarrierException e) {
       }
     }
  }
  public static void main(String[] args) {
     // 三个旅行团
     CyclicBarrier barrier = new CyclicBarrier(3);
     ExecutorService exec = Executors.newFixedThreadPool(3);
     exec.submit(new Tour(barrier, "WalkTour", timeWalk));
     exec.submit(new Tour(barrier, "SelfTour", timeSelf));
//当我们把下面的这段代码注释后，会发现，程序阻塞了，无法继续运行下去。
     exec.submit(new Tour(barrier, "BusTour", timeBus));
     exec.shutdown();
  }
} 