package com.revature.B;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BHeist {
	
	/*
	 * Max integer value for the password.
	 * (This is what the AscendingHackerThread will work through).
	 * It can be defined AFTER making that private static class.
	 */
	public static final int MAX_PASSWORD = 9999;

	public static void main(String[] args) {
		
		/*
		 *  This will randomly set the Password each 
		 *  time the main thread runs.
		 */
		
		Random random = new Random();
		
		Vault vault = new Vault(random.nextInt(MAX_PASSWORD)); // The nextInt on the random object returns a random number between 0 and arg (MAX_PASSWORD)
		System.out.println("The password is " + vault.password);
		
		List<Thread> threads = new ArrayList<>();
		
		// add the Asc, Desc Hackers, and  to our list of threads:
		threads.add(new AscendingHackerThread(vault)); // pass thru the vault they're trying to attack
		threads.add(new DescendingHackerThread(vault));
		threads.add(new PoliceThread());
		
		// iterate through list of threads and call start method on each one.
		for(Thread t : threads) {
			t.start();
		}
		
		/* ==================================
		 * ===== THEN START THE PROGRAM! ====
		 * ==================================
		 */
		
		
			
	}
	
	
	// Step 1: Vault holds our password passed thru constructor
	private static class Vault {
		
		private int password;
		
		public Vault(int password) {
			this.password = password;
		}
		
		// only one method - takes in a guess and returns true if == password
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
	
	private static class DescendingHackerThread extends HackerThread {

		public DescendingHackerThread(Vault vault) {
			super(vault);
		}
		
		@Override
		public void run() {
			for(int guess = MAX_PASSWORD; guess >= 0; guess--) {
				if(vault.isCorrectPassword(guess)) {
					System.out.println(this.getName() + " guessed the password " + guess);
					System.exit(0);
				}
			}
		}
		
	}
	
	private static class PoliceThread extends Thread {
		@Override
		public void run() {
			for(int i = 10; i > 0; i--) {
				try {
					Thread.sleep(1500); // adjust this to give the hackers a little bit more time to guess it.
				} catch (InterruptedException e) {
		
				}
				System.out.println(i + " seconds left");
			}
			
			System.out.println("Game over for you Hackers");
			System.exit(0);
		}
	}
	
	
	
	/*				
	 *     3 CONCRETE Thread Classes:
	 *     (1) Police, (2) AscHacker, and (3) DescHacker.
	 *     
	 *     Hacker Thread (abstract) has a reference to the vault obj.
	 *     Since the Asc & Desc inherit from Hacker Thread,
	 *     they also have a reference to the Vault obj.
	 *     
	 * 
	 * 
	 * 
	 * 									================	                           
	 * 									=== Runnable ===     
	 * 									================
	 * 							  			 |
	 * 							   			 |
	 * 									================	
	 * 									==== Thread ====       
	 * 									================
	 * 									  /	          \
	 * 									 /	           \
	 * 									/               \
	 * 		  			=====================	    =====================	 
	 * 		  			=== Hacker Thread ===		=== Police Thread ===
	 *  				=====================       =====================
	 *  			    /			        \
	 * 				   /			         \
	 * 	   =======================      ========================
	 * 	   === AscHackerThread ===      === DescHackerThread ===    
	 *     =======================		========================
	 */
	
	
}






















