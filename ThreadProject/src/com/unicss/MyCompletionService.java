package com.unicss;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * CompletionService的功能是以异步的方式一边生产新的任务，一边处理已完成任务的结果，这样可以将执行任务与处理任务分离开来进行处理
 * https://www.cnblogs.com/huhx/p/baseusejavaCompletionService.html
 * 使用submit()方法执行任务，使用take取得已完成的任务，并按照完成这些任务的时间顺序处理它们的结果。
 * take()方法取得最先完成任务的Future对象，谁执行时间最短谁最先返回。
 * 方法poll的作用是获取并移除表示下一个已完成任务的Future，如果不存在这样的任务，则返回null，方法poll是无阻塞的
 */
public class MyCompletionService implements Callable<String> {
private int id;

public MyCompletionService(int i){
   this.id=i;
}
public static void main(String[] args) throws Exception{
   ExecutorService service=Executors.newCachedThreadPool();
   CompletionService<String> completion=new ExecutorCompletionService<String>(service);
   for(int i=0;i<10;i++){
    completion.submit(new MyCompletionService(i));
   }
   for(int i=0;i<10;i++){
    System.out.println(completion.take().get());
   }
   service.shutdown();
}

@Override
public String call() throws Exception {
	   Integer time=(int)(Math.random()*1000);
	   try{
	    System.out.println(this.id+" start");
	    Thread.sleep(time);
	    System.out.println(this.id+" end");
	   }
	   catch(Exception e){
	    e.printStackTrace();
	   }
	   return this.id+":"+time;
	}
}