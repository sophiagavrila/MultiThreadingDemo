package com.revature.B;

public class BHeist {
	
	/*
	 * Max integer value for the password.
	 * (This is what the AscendingHackerThread will
	 * work through). -- so it can befined AFTER making that private static class.
	 */
	public static final int MAX_PASSWORD = 9999;

	public static void main(String[] args) {

	}
	
	
	// Step 1: Valut holds our password passed thru constructor
	private static class Vault {
		
		private int password;
		
		public Vault(int password) {
			this.password = password;
		}
		
		// only one method - takes in a guess and returns true if == passowrd
		public boolean isCorrectPassword(int guess) {
			try {
				Thread.sleep(5); // delays response by 5 miliseconds (slows down hacker)
			} catch (InterruptedException e) {
				
			}
			
			return this.password == guess;
		}
	}
	
	private static abstract class HackerThread extends Thread {
		protected Vault vault;
		
		public HackerThread(Vault vault) {
			this.vault = vault;
			this.setName(this.getClass().getSimpleName());
			this.setPriority(Thread.MAX_PRIORITY); // we use Thread instead of this because it must be accessed in a static way.
		}
		
		// because it extends thread, we can override methods
		public void start() {   // @Override annotation informs the compiler that the element is meant to override an element declared in a superclass
			System.out.println("Starting thread " + this.getName());
			super.start();
		}
		
		/*
		 * NEXT STEPS: Now that we have the generic HackerThread, we can
		 * prepare the first concrete class that will inherit all of its functionalities.
		 */
		
	}
	
	/*
	 *  NEXT: AscendingHackerThread: Extends HackerTHread -- guesses our password by 
	 *  iterating through all the numbers in asc order. Make sure that you set the MAX_PASSWORD field of Heist Class above.
	 */
	
	private static class AscendingHackerThread extends HackerThread {

		public AscendingHackerThread(Vault vault) {
			super(vault);
		}
		
		@Override
		public void run() {
			for(int guess = 0; guess < MAX_PASSWORD; guess ++) {
				if (vault.isCorrectPassword(guess)) {
					System.out.println(this.getName() + " guessed the password " + guess);
					System.exit(0);  /*
										System.exit() method exits current program by terminating running JVM										
										This method takes a status code. A non-zero value of status code 
										is generally used to indicate abnormal termination.
										
										exit(0) : Generally used to indicate successful termination.
										exit(-1) or any other non-zero value – Generally indicates unsuccessful termination.
									*/
				}
			}
		}
		
	} 
	
	
}






















