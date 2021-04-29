package com.example.D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C_SolutionJoinExample {

	/*
	 * Make sure you add a throws clause to the main method since
	 * the addition of a join() method could cause an InterruptedException.
	 */
	public static void main(String[] args) throws InterruptedException { // important!

		List<Long> inputNumbers = Arrays.asList(0L, 3535L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);

		List<B_FactorialThread> threads = new ArrayList<>();
		
		for(long num : inputNumbers) {
			threads.add(new B_FactorialThread(num));
		}
		
		
		for (Thread t : threads) {
			t.setDaemon(true); // we will also set every thread to be a Daemon
			t.start();
		}
		
		/*	NEXT:
		 *  iterate over every thread and call the join() method.
		 *  The join() method will return ONLY when the thread has terminated.
		 *  
		 *  By the time the main method has terminated, all of the values will be
		 *  fully calculated, as each of our FactorialThreads has finished its work.
		
		
					===========================
					======= EXPLANATION =======
					===========================
		
	
		 * Join is a synchronization method that blocks the calling thread 
		 * (calling thread == the thread that calls the method) until the thread whose 
		 * Join method is called has completed. 
		 * 
		 * Join forms an agreement (a.k.a synchronization) with the main thread
		 * so that the main thread doesn't terminate until all of the FactorialThreads have.
		 */
		
		for(Thread t : threads) {
			t.join(); //first show join without a "toleration time-limit"
			//t.join(2000); this timeout allows us the exit the thread if it doesn't get completed in time.
		}
		
		for (int i = 0; i < inputNumbers.size(); i++) {
			
			B_FactorialThread factorialThread = threads.get(i);
			
			if(factorialThread.isFinished()) { // main thread is checking for results.  Second part of race condition.
				System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
			} else {
				System.out.println("the calculation for " + inputNumbers.get(i) + " is still in progress.");
			}
		}
		
	}
}
