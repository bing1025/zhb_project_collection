package com.unicss;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * https://www.cnblogs.com/liuling/p/2013-8-20-01.html
 * BlockingQueue,如果BlockQueue是空的,从BlockingQueue取东西的操作将会被阻断进入等待状态,直到BlockingQueue进了东西才会被唤醒.同样,如果BlockingQueue是满的,任何试图往里存东西的操作也会被阻断进入等待状态,直到BlockingQueue里有空间才会被唤醒继续操作. 
    使用BlockingQueue的关键技术点如下: 
    1.BlockingQueue定义的常用方法如下: 
        1)add(anObject):把anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则报异常 
        2)offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false. 
        3)put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续. 
        4)poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null 
        5)take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到Blocking有新的对象被加入为止 
    2.BlockingQueue有四个具体的实现类,根据不同需求,选择不同的实现类 
        1)ArrayBlockingQueue:规定大小的BlockingQueue,其构造函数必须带一个int参数来指明其大小.其所含的对象是以FIFO(先入先出)顺序排序的. 
        2)LinkedBlockingQueue:大小不定的BlockingQueue,若其构造函数带一个规定大小的参数,生成的BlockingQueue有大小限制,若不带大小参数,所生成的BlockingQueue的大小由Integer.MAX_VALUE来决定.其所含的对象是以FIFO(先入先出)顺序排序的 
        3)PriorityBlockingQueue:类似于LinkedBlockQueue,但其所含对象的排序不是FIFO,而是依据对象的自然排序顺序或者是构造函数的Comparator决定的顺序. 
        4)SynchronousQueue:特殊的BlockingQueue,对其的操作必须是放和取交替完成的. 
    3.LinkedBlockingQueue和ArrayBlockingQueue比较起来,它们背后所用的数据结构不一样,导致LinkedBlockingQueue的数据吞吐量要大于ArrayBlockingQueue,但在线程数量很大时其性能的可预见性低于ArrayBlockingQueue. 
 */

public class MyBlockingQueue extends Thread {
public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);
private int index;
public MyBlockingQueue(int i) {
   this.index = i;
}
public void run() {
   try {
    queue.put(String.valueOf(this.index));
    System.out.println("{" + this.index + "} in queue!");
   } catch (Exception e) {
    e.printStackTrace();
   }
}
public static void main(String args[]) {
   ExecutorService service = Executors.newCachedThreadPool();
   for (int i = 0; i < 10; i++) {
    service.submit(new MyBlockingQueue(i));
   }
   Thread thread = new Thread() {
    public void run() {
     try {
      while (true) {
       Thread.sleep((int) (Math.random() * 1000));
       System.out.println("======="+MyBlockingQueue.queue.size());
       if(MyBlockingQueue.queue.isEmpty())
        break;
       String str = MyBlockingQueue.queue.take();
       System.out.println(str + " has take!");
      }
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
   };
   service.submit(thread);
   service.shutdown();
}
}