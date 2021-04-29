package com.example.D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_JoinExample {

	public static void main(String[] args) {

		/*
		 * The following List will be provided as input to a thread that will calculate
		 * the factorial of each number.
		 * 
		 * A Factorial is: the product of all positive integers less than or equal to a
		 * given positive integer e.g: !4 = 24 because (1 x 2 x 3 x 4 = 24).
		 */
		List<Long> inputNumbers = Arrays.asList(0L, 3535L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
		// We want to calculate: !0L, !3535L, !35435L, !2324L, !4656L, !23L, !2435L, !5566...
		
		/*======================================================
		 * ====== Step 2: Make B_FactorialThread Class =========
		 * === (Then pick back up on line 29 in this class) ====
		 *======================================================
		*/
		
		// 1. Create a List of threads, and create a new FactorialThread for
		// each input number in the inputNumbers List.
		List<B_FactorialThread> threads = new ArrayList<>();
		
		for(long num : inputNumbers) {
			threads.add(new B_FactorialThread(num));
		}
		
		// 2. iterate over all threads and start them (then factorial thread + main thread are running concurrently
		for (Thread t : threads) {
			t.start(); // begins race condition between FactorialThreads and Main thread.
		}
		
		// 3. Check to see the result ro is the Factorial thread hasn't yet finished.
		for (int i = 0; i < inputNumbers.size(); i++) {
			B_FactorialThread factorialThread = threads.get(i);
			
			if(factorialThread.isFinished()) { // main thread is checking for results.  Second part of race condition.
				System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
			} else {
				System.out.println("the calculation for " + inputNumbers.get(i) + " is still in progress.");
			}
			/*
			 * Here we have a race condition between the FacoritalThreads and the Main thread.
			 * The main thread is checking for a result, but the FactorialThreads are still calculating.
			 * 
			 * What we're witnessing is that the mian Thread is thread B, checking the result of Thread A, 
			 * 
			 * In order to resolve this, we must implement the join method so that the main method will WAIT
			 * for Thread A to complete before checking its results.
			 * 
			 * =================================
			 * === Go to C_SolveJoinExample ====
			 * =================================
			 */
		}
		
	}
}
