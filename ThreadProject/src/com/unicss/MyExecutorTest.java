package com.unicss;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/*
 * ExecutorService 的理解与使用   https://blog.csdn.net/bairrfhoinn/article/details/16848785
 * 
 * 接口 java.util.concurrent.ExecutorService 表述了异步执行的机制，并且可以让任务在后台执行。壹個 ExecutorService 实例因此特别像壹個线程池。事实上，在 java.util.concurrent 包中的 ExecutorService 的实现就是壹個线程池的实现。
 * 
 * execute(Runnable)
方法 execute(Runnable) 接收壹個 java.lang.Runnable 对象作为参数，并且以异步的方式执行它。

使用这种方式没有办法获取执行 Runnable 之后的结果，如果你希望获取运行之后的返回值，就必须使用 接收 Callable 参数的 execute() 方法，后者将会在下文中提到。
submit(Runnable)
方法 submit(Runnable) 同样接收壹個 Runnable 的实现作为参数，但是会返回壹個 Future 对象。这個 Future 对象可以用于判断 Runnable 是否结束执行。

submit(Callable)
方法 submit(Callable) 和方法 submit(Runnable) 比较类似，但是区别则在于它们接收不同的参数类型。Callable 的实例与 Runnable 的实例很类似，但是 Callable 的 call() 方法可以返回壹個结果。方法 Runnable.run() 则不能返回结果。

Callable 的返回值可以从方法 submit(Callable) 返回的 Future 对象中获取

ExecuteService 服务的关闭
当使用 ExecutorService 完毕之后，我们应该关闭它，这样才能保证线程不会继续保持运行状态。 
举例来说，如果你的程序通过 main() 方法启动，并且主线程退出了你的程序，如果你还有壹個活动的 ExecutorService 存在于你的程序中，那么程序将会继续保持运行状态。存在于 ExecutorService 中的活动线程会阻止Java虚拟机关闭。
为了关闭在 ExecutorService 中的线程，你需要调用 shutdown() 方法。ExecutorService 并不会马上关闭，而是不再接收新的任务，壹但所有的线程结束执行当前任务，ExecutorServie 才会真的关闭。所有在调用 shutdown() 方法之前提交到 ExecutorService 的任务都会执行。
如果你希望立即关闭 ExecutorService，你可以调用 shutdownNow() 方法。这個方法会尝试马上关闭所有正在执行的任务，并且跳过所有已经提交但是还没有运行的任务。但是对于正在执行的任务，是否能够成功关闭它是无法保证的，有可能他们真的被关闭掉了，也有可能它会壹直执行到任务结束。这是壹個最好的尝试。


 */

public class MyExecutorTest extends Thread {
	public static AtomicLong i = new AtomicLong(0);
	public void run() {
	    try {
	    	while (i.incrementAndGet() <= 1000000) {
	    		System.out.println(i);
	    	}
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	public static void main(String args[]) {
	    ExecutorService service = Executors.newFixedThreadPool(10);
	    for (int i=0; i<10; i++) {
	     service.execute(new MyExecutorTest());
	    }
	    service.shutdown();
	}

}
