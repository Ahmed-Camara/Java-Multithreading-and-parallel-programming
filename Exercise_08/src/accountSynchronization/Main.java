package accountSynchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	private static Account account = new Account();
	public static void main(String[] args) {
		
		ExecutorService executor =
				Executors.newFixedThreadPool(2);
		
		executor.execute(new DepositTask());
		executor.execute(new withdrawTask());
		
		executor.shutdown();
		
		System.out.println("Thread 1\t\tThread 2 \t\tBalance");
	}
	
	public static class DepositTask implements Runnable{

		@Override
		public void run() {
			
			try {
				while(true) {
					account.deposit((int)(Math.random() * 10) + 1);
					Thread.sleep(1000);
				}
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static class withdrawTask implements Runnable{

		@Override
		public void run() {
			while(true) {
				account.withdraw((int)(Math.random() * 10) + 1);
			}
		}
		
	}
	
	private static class Account{
		private int balance = 0;
		public int getBalance() {
			return this.balance;
		}
		
		public void withdraw(int amount) {
			
			synchronized(this) {
				try {
					while(balance < amount) {
						System.out.println("\t\t\tWait for a deposit");
						this.wait();
						
					}
					
					balance -= amount;
					System.out.println("\t\t\tWithdraw "+ amount + " \t\t"+
							getBalance());
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		public void deposit(int amount) {
			
			synchronized(this) {
				
				balance += amount;
				System.out.println("Deposit "+amount+" \t\t\t\t\t"+
						getBalance());
				
				this.notifyAll();
			}
		}
	}
}
