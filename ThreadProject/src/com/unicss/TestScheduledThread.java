package com.unicss;

/*
 * ScheduledExecutorService的主要作用就是可以将定时任务与线程池功能结合使用  
 *  https://www.cnblogs.com/huhx/p/baseusejavaScheduledExecutorService.html
 *  Timer和ScheduledExecutorService都可以用来做定时任务，有管理任务延迟执行("如1000ms后执行任务")以及周期性执行("如每500ms执行一次该任务")。但至从JDK1.5之后，建议采用ScheduledExecutorService。

原因如下：

1.Timer对调度的支持是基于绝对时间,而不是相对时间的，由此任务对系统时钟的改变是敏感的;但ScheduledThreadExecutor只支持相对时间。

2.如果TimerTask抛出未检查的异常，Timer将会产生无法预料的行为。Timer线程并不捕获异常，所以 TimerTask抛出的未检查的异常会终止timer线程。此时，已经被安排但尚未执行的TimerTask永远不会再执行了，新的任务也不能被调度了。

3.Timer里面的任务如果执行时间太长，会独占Timer对象，使得后面的任务无法几时的执行 ，ScheduledExecutorService不会出现Timer的问题(除非你只搞一个单线程池的任务区)。
 */
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
public class TestScheduledThread {
public static void main(String[] args) {
   final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
   final Runnable beeper = new Runnable() {
	    int count = 0;
	    public void run() {
	     System.out.println(new Date() + " beep " + (++count));
	    }
};
   // 1秒钟后运行，并每隔2秒运行一次
   final ScheduledFuture beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 2, SECONDS);
   // 2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行
   final ScheduledFuture beeperHandle2 = scheduler.scheduleWithFixedDelay(beeper, 2, 5, SECONDS);
   // 30秒后结束关闭任务，并且关闭Scheduler
   scheduler.schedule(new Runnable() {
    public void run() {
     beeperHandle.cancel(true);
     beeperHandle2.cancel(true);
     scheduler.shutdown();
    }
   }, 30, SECONDS);
}
}