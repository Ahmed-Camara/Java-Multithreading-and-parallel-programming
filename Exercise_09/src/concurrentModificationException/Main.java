package concurrentModificationException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * The program create and launches 1000 threads concurrently as you will see,
 	at a certain point the program will throw a concurrentModificationException.
 	The reason is that : the iterator as we know is fail-fast which means,
 	when we are using an iterator to traverse the set collection while,
 	the underlying is being modified by another thread,then the iterator will
 	immediately fail by throwing concurrentModificationException.
 	We can use Synchronized Collections to overcome to this situation.
 	
 * */
public class Main {
	
	private static Set<Integer> set = new HashSet<>();
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
				Iterator<Integer> iterator = set.iterator();
				while(iterator.hasNext()) {
					System.out.println(iterator.next());
				}
		}
		
	}

}
