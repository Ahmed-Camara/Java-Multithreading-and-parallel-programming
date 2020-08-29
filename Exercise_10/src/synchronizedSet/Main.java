package synchronizedSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 	
 * To correct concurrentModificationException problem, we can use
 	Synchronized Collections which acquire a lock on the object when traversing it.
 	
 * */
public class Main {
	
	private static Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for(int i = 0; i < 1000; i++) {
			executor.execute(new FillTask());
			executor.execute(new traverseTask());
		}
		executor.shutdown();
	}
	public static class FillTask implements Runnable{

		@Override
		public void run() {
			
			
				set.add((int) (Math.random() * 1000) + 1);
				System.out.println("Number added");
				try {
					Thread.sleep(1000);
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		
		
	}
	
	public static class traverseTask implements Runnable{

		@Override
		public void run() {
			
				try {
					Thread.sleep(1000);
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				synchronized(set) {
					Iterator<Integer> iterator = set.iterator();
					while(iterator.hasNext()) {
						System.out.println(iterator.next());
					}
				}
		}
		
	}

}

