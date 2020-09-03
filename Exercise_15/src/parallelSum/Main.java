package parallelSum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
	
	private static int size = 9_000_000;
	
	public static void main(String[] args) {
		
		double []list = new double[size];
		
		for(int i = 0; i < list.length; i++)
            list[i]= (int)(Math.random() * 10000000);
		double sum = 0;
		long start = System.currentTimeMillis();
		sum = sequentialSum(list);
		long end = System.currentTimeMillis();
		long difference = end - start;
		System.out.println("\t\tSEQUENTIAL ALGORITHM");
		System.out.println("SUM is equal to : "+sum);
		System.out.println("Time using "
				+Runtime.getRuntime().availableProcessors()+" processors is : "+difference
				+" milliseconds");
		
		System.out.println();
		
		
		start = System.currentTimeMillis();
		sum = parallelSum(list);
		end = System.currentTimeMillis();
		difference = end - start;
		System.out.println("\t\tPARALLEL ALGORITHM");
		System.out.println("SUM is equal to : "+sum);
		System.out.println("Time using "
				+Runtime.getRuntime().availableProcessors()+" processors is : "+difference
				+" milliseconds");
		
	}
	public static long sequentialSum(double []list) {
		long sum = 0;
		int listLength = list.length;
		for(int i = 0; i < listLength; i++) {
			
			sum += list[i];
		}
		
		return sum;
	}
	public static long parallelSum(double []list) {
		
		RecursiveTask<Long> task = new ParallelSumTask(list,0,list.length);
		ForkJoinPool pool = new ForkJoinPool();

		return pool.invoke(task);
	}
	private static class ParallelSumTask extends RecursiveTask<Long>{
		
		private static final long serialVersionUID = 1L;
		private final int THRESHOLD = 50000;
		private double []list;
		private int low;
		private int high;
		
		ParallelSumTask(double []list,int low,int high){
			this.list = list;
			this.low = low;
			this.high = high;
		}
		@Override
		protected Long compute() {
			
			if(high - low < THRESHOLD) {
				
				long sum = 0;
				for(int i = low; i < high; i++) {
					sum += list[i];
				}
				return sum;
			}else {
				
				int middle = (high + low) / 2;
				RecursiveTask<Long> left = new ParallelSumTask(list,low,middle);
				RecursiveTask<Long> right = new ParallelSumTask(list,middle, high);
				
				left.fork();
				right.fork();
				
				return left.join() + right.join();
			}
		}
		
	}

}
