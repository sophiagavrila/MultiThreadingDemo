package com.example.D;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
 * ===========================================================
 * =============== MultiThreaded Calculation =================
 * ===========================================================
 * In this coding exercise we will you all the knowledge from the 
 * previous lectures.Before taking the exercise make sure you review
 *  the following topics in particular:
 * 
 * 1. Thread Creation - how to create and start a thread using the Thread class and the start() method.
 *
 * 2. Thread Join - how to wait for another thread using the Thread.join() method.
 * 	                Remember: Remember that .join() tells the calling program to wait until the thread
					          object upon which the join method has been called has finished its work.
 * 
 * In this exercise we will efficiently calculate the following result = base1 ^ power1 + base2 ^ power2
 * Where a^b means: a raised to the power of b.
 *
 * For example 10^2 = 100
 * 
 * We know that raising a number to a power is a complex computation, so we we like to execute:
 * 
 * 			result1 = x1 ^ y1 
 * 
 * 			result2 = x2 ^ y2
 * 
 * In parallel, and combine the result in the end : result = result1 + result2
 * This way we can speed up the entire calculation.
 * 
 * NOTE: base1 >= 0, base2 >= 0, power1 >= 0, power2 >= 0
 * ===========================================================
 * ===========================================================
 */

/*
 * =================================
 * ========== Challenge ============
 * =================================
 */


public class MultiThreadedCaculationChallenge {

	public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {

		BigInteger result = BigInteger.ZERO; /* ! I'm only initializing this here so my compiler doens't yell at me.
											  * When presenting the challenge, it's fine to leave it un-initialized
											  * i.e ... BigInteger result; 
											  */
		/*
		 * Calculate result = ( base1 ^ power1 ) + (base2 ^ power2). Where each
		 * calculation in is calculated on a different thread.
		 */
		return result;
	}

	private static class PowerCalculatingThread extends Thread {

		private BigInteger result = BigInteger.ONE;
		private BigInteger base;
		private BigInteger power;

		public PowerCalculatingThread(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			/*
			 * Implement the calculation of result = base ^ power
			 */
		}

		public BigInteger getResult() {
			return result;
		}
	}
}

/*
 * ==================================
 * ========== Solution A ============
 * ==================================
 */
class SoltuionA {

	public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {

		BigInteger result;

		PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
		PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);

		thread1.start();
		thread2.start();

		try {

			thread1.join();
			thread2.join();

		} catch (InterruptedException e) { // alternatively this could be thrown at the top of the method.

			e.printStackTrace();
		}

		result = thread1.getResult().add(thread2.getResult());

		return result;
	}

	private static class PowerCalculatingThread extends Thread {

		private BigInteger result = BigInteger.ONE;
		private BigInteger base;
		private BigInteger power;

		public PowerCalculatingThread(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			result = BigInteger.ONE;

			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
				result = result.multiply(base);
			}
		}

		public BigInteger getResult() {
			return result;
		}
	}
}

/*
 * ==================================
 * ========== Solution B ============
 * ==================================
 */

/*
 * Another way of solving the solution (less advanced than the first, as we
 * transform BigInteger to Long in order to iterate over values. Also, instead
 * of adding throws declaration in the method itself, we append a try/catch
 * block.
 */
class SolutionB {

	public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
			throws InterruptedException {

		BigInteger result = BigInteger.ZERO;
		/*
		 * Calculate result = ( base1 ^ power1 ) + (base2 ^ power2). Where each
		 * calculation in (..) is calculated on a different thread
		 */

		PowerCalculatingThread threadA = new PowerCalculatingThread(base1, power1);
		PowerCalculatingThread threadB = new PowerCalculatingThread(base2, power2);

		List<PowerCalculatingThread> threads = new ArrayList<>();
		threads.add(threadA);
		threads.add(threadB);

		for (PowerCalculatingThread t : threads) {
			t.start();
			t.join(); /*
						 * Remember that .join() tells the calling program to wait until the thread
						 * object upon which the join method has been called has finished its work.
						 */
			result = result.add(t.getResult());
		}

		return result;
	}

	private static class PowerCalculatingThread extends Thread {

		private BigInteger result = BigInteger.ONE;
		private BigInteger base;
		private BigInteger power;

		public PowerCalculatingThread(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			/*
			 * Implement the calculation of result = base ^ power
			 */
			for (long i = power.longValue(); i >= 1; i--) {
				result = result.multiply(base);
			}

		}

		public BigInteger getResult() {
			return result;
		}
	}
}