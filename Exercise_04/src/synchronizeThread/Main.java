package synchronizeThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * 1. Without Synchronization
 * All 1000 threads have access concurrently to the
	same resource and this may generate a wrong
	result as the data get corrupted.
 * 
 * 2. With Synchronization
 * All 1000 threads have access sequentially to the shared resource
 	one after the another and final result will be right as the data
 	will not get corrupted. Synchronized keyword make sure to respect that.
 	When one thread access the resource, and another thread want to access it also,
 	it will be blocked untill the thread that already accessed the resource release it.
 	Once it release the blocking thread will get access to it also one by one.
 	
 * */
public class Main {
	private static Integer wrap_sum;
	private static sharedResource obj = new sharedResource();
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i = 0; i < 1000; i++) {
			executor.execute(new addSumTask());
		}
		executor.shutdown();
		
		while(!executor.isTerminated());
		
		System.out.println("The final sum value is : "+wrap_sum);
	}
	
	private static class addSumTask implements Runnable{
		
		
		@Override
		public void run() {
			
			//1. With Synchronization
			obj.incrementSumVariable();
			
			
			//2 . With Synchronization
			
			synchronized(obj) {
				obj.incrementSumVariable();
			}
		}
		
	}
	
	private static class sharedResource{
		private int sum = 0;
		public sharedResource() {
			
		}
		public void incrementSumVariable() {
			sum++;
			
			wrap_sum = sum;
		}
	}
}
