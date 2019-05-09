package com.unicss;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
 * Semaphore的介绍和使用
 * http://www.itzhai.com/the-introduction-and-use-of-semaphore.html
 * 
 * 一个计数信号量。从概念上讲，信号量维护了一个许可集。如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。
 * 每个 release() 添加一个许可，从而可能释放一个正在阻塞的获取者。
 * 但是，不使用实际的许可对象，Semaphore 只对可用许可的号码进行计数，并采取相应的行动。
 * 拿到信号量的线程可以进入代码，否则就等待。通过acquire()和release()获取和释放访问许可。
 */

public class MySemaphore extends Thread {
Semaphore position;
private int id;
public MySemaphore(int i,Semaphore s){
    this.id=i;
    this.position=s;
}
public void run(){
    try{
     if(position.availablePermits()>0){
      System.out.println("顾客["+this.id+"]进入厕所，有空位");
     }
     else{
      System.out.println("顾客["+this.id+"]进入厕所，没空位，排队");
     }
     position.acquire();
     System.out.println("顾客["+this.id+"]获得坑位");
     Thread.sleep((int)(Math.random()*1000));
     System.out.println("顾客["+this.id+"]使用完毕");
     position.release();
    }
    catch(Exception e){
     e.printStackTrace();
    }
}
public static void main(String args[]){
    ExecutorService list=Executors.newCachedThreadPool();
    Semaphore position=new Semaphore(2);
    for(int i=0;i<10;i++){
     list.submit(new MySemaphore(i+1,position));
    }
    list.shutdown();
    position.acquireUninterruptibly(2);
    System.out.println("使用完毕，需要清扫了");
    position.release(2);
}
}