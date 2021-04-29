package com.example.C;

import java.math.BigInteger;

public class B_InterruptionDemo {

	public static void main(String[] args) {
		/*
		 * Note that if we pass in large numbers, it takes a very long time.
		 */
		Thread compThread = new Thread(new LongComputationTask(new BigInteger("4"), new BigInteger("2")));
		
		compThread.start(); // === THEN RUN! ====
		
		/*
		 * Note that if we pass in large numbers, it takes a very long time.
		 * 
		 * But what if we call interrupt() on this specific thread?
		 */
		compThread.interrupt(); // Note that running interrupt() in this case is not enough
								// The interrupt is sent, but we have no logic to handle it.
								// (until we fix it in the method signature)
								// === Go back to pow method and add the if statement! ===
	}
	
	private static class LongComputationTask implements Runnable {
		
		private BigInteger base;
		private BigInteger power;

		
		@Override
		public void run() {
		
			// ====== FINAL STEP BEFORE RUNNING THE THREAD =======
			System.out.println(base + "^" + power + " = " + pow(base, power));
			
		}
		

		public LongComputationTask(BigInteger base, BigInteger power) {
			super();
			this.base = base;
			this.power = power;
		}



		// We calculate a num to a given power
		// FIRST, go up an  declare base and power as private members
		// within the class.
		
		// 4^2 == 16
		private BigInteger pow(BigInteger base, BigInteger power) {
			
			/*
			 *  This method will raise the base num to the given power.
			 *  
			 *  The compareTo() method is just checking that we don't
			 *  raise the base to a number > power, because a return 
			 *  value of 0 in compareTo() means "equal to".
			 */
			
			BigInteger result = BigInteger.ONE;
			
			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
			
				/*
				 * ==== add this step AT THE END of demo ====
				 */
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("Thread prematurely interrupted");
					return BigInteger.ZERO;
				}
				
				result = result.multiply(base);
			}
			
			return result;
			
		}
		
	}

}
