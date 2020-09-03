package parallelMultiplyMatrix;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Main {
	
	private static int size = 6000;
	
	public static void main(String[] args) {
		
		double [][]a = new double[size][size];
		double [][]b = new double[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				a[i][j] = (int)(Math.random() * size);
				b[i][j] = (int)(Math.random() * size);
			}
		}
		System.out.println();
		double [][]result;
		long start = System.currentTimeMillis();
		result = sequentialMatrixMultiplication(a,b);
		long end = System.currentTimeMillis();
		long difference = end - start;
		System.out.println();
		System.out.println("\t\tSEQUENTIAL ALGORITHM");
		System.out.println("Time using "
				+Runtime.getRuntime().availableProcessors()+" processors is : "+difference
				+" milliseconds");
		
		
		
		System.out.println();
		System.out.println();
		
		
		start = System.currentTimeMillis();
		result = parallelMatrixMultiplication(a,b);
		end = System.currentTimeMillis();
		difference = end - start;
		System.out.println();
		System.out.println("\t\tPARALLEL ALGORITHM");
		System.out.println("Time using "
				+Runtime.getRuntime().availableProcessors()+" processors is : "+difference
				+" milliseconds");
	}
	
	private static double [][] sequentialMatrixMultiplication(double [][]a,double [][]b){
		double [][]result = new double[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				
				result[i][j] = a[i][j] * b[i][j];
			}
		}
		return result;
	}
	
	private static double [][]parallelMatrixMultiplication(double [][]a,double [][]b){
		double [][]result = new double[a.length][a[0].length];
		RecursiveAction task = new ParallelMatrixMultiplications(a,b,result,false,0);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(task);
		return result;
	}
	
	private static class ParallelMatrixMultiplications extends RecursiveAction{

		private static final long serialVersionUID = 1L;
		private double [][]a;
		private double [][]b;
		private double [][]result;
		private boolean addSingleRow;
		private int index;
		
		ParallelMatrixMultiplications(double [][]a,double [][]b,double [][]result,boolean addRow,int index){
			this.a = a;
			this.b = b;
			this.result = result;
			this.addSingleRow = addRow;
			this.index = index;
		}
		@Override
		protected void compute() {
			
			
			if(addSingleRow == true) {
				
				for(int i = 0; i < size; i++) {
					result[index][i] = a[index][i] * b[index][i];
				}
			}else {
				
				RecursiveAction []tasks = new ParallelMatrixMultiplications[size];
				
				for(int i = 0; i < size; i++) {
					
					tasks[i] = new ParallelMatrixMultiplications(a,b,result,true,i);
				}
				
				invokeAll(tasks);
				
			}
		}

	}
}
