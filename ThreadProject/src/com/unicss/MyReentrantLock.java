package com.unicss;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/*ReentrantLock的使用  https://www.jianshu.com/p/155260c8af6c 
 * 在jdk1.5里面，ReentrantLock的性能是明显优于synchronized的，但是在jdk1.6里面，synchronized做了优化，他们之间的性能差别已经不明显了。
 * 
 * ReentrantLock并不是一种替代内置加锁的方法，而是作为一种可选择的高级功能。
相比于synchronized，ReentrantLock在功能上更加丰富，它具有可重入、可中断、可限时、公平锁等特点。
 */
public class MyReentrantLock extends Thread{
TestReentrantLock lock;
private int id;
public MyReentrantLock(int i,TestReentrantLock test){
    this.id=i;
    this.lock=test;
}
public void run(){
    lock.print(id);
}
public static void main(String args[]){
    ExecutorService service=Executors.newCachedThreadPool();
    TestReentrantLock lock=new TestReentrantLock();
    for(int i=0;i<10;i++){
     service.submit(new MyReentrantLock(i,lock));
    }
    service.shutdown();
}
}
class TestReentrantLock{
private ReentrantLock lock=new ReentrantLock();
public void print(int str){
    try{
     lock.lock();
     System.out.println(str+"获得");
     Thread.sleep((int)(Math.random()*1000));
    }
    catch(Exception e){
     e.printStackTrace();
    }
    finally{
     System.out.println(str+"释放");
     lock.unlock();
    }
}
}