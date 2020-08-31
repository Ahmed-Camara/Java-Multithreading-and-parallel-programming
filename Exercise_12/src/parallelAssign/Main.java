package parallelAssign;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Main {
	private final static int size = 9_000_000;
	public static void main(String[] args) {
		
		
		int []list = new int[size];
		
		long start = System.currentTimeMillis();
		sequentialAssign(list);
		long end = System.currentTimeMillis();
		System.out.println("Time to Assign values to list sequentially with "
				+ Runtime.getRuntime().availableProcessors()+" available"
						+ " processors is : "+(end - start));
		
		
		start = System.currentTimeMillis();
		parallelAssign(list);
		end = System.currentTimeMillis();
		System.out.println("Time to Assign values to list parallely with "
				+ Runtime.getRuntime().availableProcessors()+" available"
						+ " processors is : "+(end - start));
	}
	
	public static void sequentialAssign(int []list) {
		
		for(int i = 0; i < list.length; i++) {
			list[i] =(int)( Math.random() * size);
		}
	}
	
	public static void parallelAssign(int []list) {
		
		RecursiveAction task = new AssignTask(list,0,list.length);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(task);
	}
	
	
	private static class AssignTask extends RecursiveAction{
		
		private static final long serialVersionUID = 1L;
		private final int THRESHOLD = 5000;
		private int []list;
		private int low;
		private int high;
		AssignTask(int []list,int low, int high){
			this.list = list;
			this.low = low;
			this.high = high;
		}
		@Override
		protected void compute() {
			Random random = new Random();
			if(high - low < THRESHOLD) {
				
				for(int i = low; i < high; i++) {
					list[i] = random.nextInt();
				}
			}else {
				
				int mid = (high + low) / 2;
				RecursiveAction first = new AssignTask(list,low,mid);
				RecursiveAction second = new AssignTask(list,mid,high);
				invokeAll(first,second);
			}
		}
		
	}

}
