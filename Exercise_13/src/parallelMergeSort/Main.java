package parallelMergeSort;


import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Main {

	public static void main(String[] args) {
		
		final int SIZE = 9_000_000;
		
		Integer []list1 = new Integer[SIZE]; 
		
		for(int i = 0; i < list1.length; i++) {
			list1[i] = (int)(Math.random() * 10000000);
		}
		
		
		long before = System.currentTimeMillis();
		MergeSort.mergeSort(list1,new ValueComparator());
		long after = System.currentTimeMillis();
		long difference = after - before;
		System.out.println("Time with Sequential Merge sort using "+
				 Runtime.getRuntime().availableProcessors()+" available processors is : "
				+difference+" milliseconds");
		
		
		 before = System.currentTimeMillis();
		 parallel(list1);
		 after = System.currentTimeMillis();
		 difference = after - before;
		 System.out.println("Time with parallel Merge sort using "+
				 Runtime.getRuntime().availableProcessors()+" available processors is : "
				+difference+" milliseconds");

	}
	
	public static<E extends Comparable<E>> void parallel(E []list1) {
		
		RecursiveAction mainTask = new sortTask(list1);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}

}

class sortTask<E> extends RecursiveAction{
	

	private static final long serialVersionUID = 1L;
	private final int THRESHOLD = 500;
	private E []list;
	
	public sortTask(E []list) {
		this.list = list;
	}

	@Override
	protected void compute() {
		
		
		if(list.length < THRESHOLD) {
			Arrays.sort(list);
		}else {
			
			//int[] firstHalf = new int[list.length/2];
			E[]firstHalf = (E[]) new Object[list.length/2];
			System.arraycopy(list, 0, firstHalf, 0, list.length/2);
			
			int len = list.length - list.length / 2;
		//	int []secondHalf = new int[len];
			E []secondHalf = (E[])new Object[len];
			System.arraycopy(list, list.length / 2, secondHalf, 0, len);
			
			invokeAll(new sortTask(firstHalf),new sortTask(secondHalf));
			
			MergeSort.merge(firstHalf,secondHalf,list,new ValueComparator());
		}
	}
	
}

class MergeSort<E>{
	
	public static <E> void mergeSort(E []list,Comparator<? super E> comparator) {
		if(list.length > 1) {
			E[] firstHalf = (E[]) new Object[list.length/2];
			System.arraycopy(list, 0, firstHalf, 0, list.length/2);
			
			
			mergeSort(firstHalf,comparator);
			
			int len = list.length - list.length / 2;
			E []secondHalf =(E[]) new Object[len];
			
			System.arraycopy(list, list.length / 2, secondHalf, 0, len);
			
			mergeSort(secondHalf,comparator);
			
			merge(firstHalf,secondHalf,list,comparator);
		}
	}

	public static<E> void merge(E[] list1, E[] list2, E[] temp,
			Comparator<? super E> comparator) {
		
		int current1 = 0;
		int current2 = 0;
		int current3 = 0;
		
		while(current1 < list1.length && current2 < list2.length) {
			
			if(comparator.compare(list1[current1], list2[current2]) < 0) {
				temp[current3++] = list1[current1++];
			}else {
				temp[current3++] = list2[current2++];
			}
		}
		
		while(current1 < list1.length) {
			temp[current3++] = list1[current1++];
		}
		
		while(current2 < list2.length) {
			temp[current3++] = list2[current2++];
		}
	}
	
	
}

class ValueComparator<E> implements Comparator<E>{

	@Override
	public int compare(E o1, E o2) {
		
		if(o1.equals(o2)) {
			return 0;
		}
		return -1;
	}
	
}
