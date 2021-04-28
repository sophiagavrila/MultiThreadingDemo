package com.example.D;

import java.math.BigInteger;

public class C_DaemonDemo {

	// add the throws clause
	public static void main(String[] args) throws InterruptedException{

		Thread compThread = new Thread(new LongComputationTask(new BigInteger("20000000"), new BigInteger("30000000")));
		
		/*
		 * Here we set the LongComputationTask Thread as a Daemon Thread.
		 * Since there's nothing in the main method to be run, when the 
		 * main thread quits, the app exits.
		 * 
		 * And when the app exits, the thread is killed.
		 */
		compThread.setDaemon(true);
		compThread.start();
		compThread.sleep(500);
		compThread.interrupt(); 
	}
	
	private static class LongComputationTask implements Runnable {
		
		private BigInteger base;
		private BigInteger power;

		
		@Override
		public void run() {
			System.out.println(base + "^" + power + " = " + pow(base, power));
			
		}
		

		public LongComputationTask(BigInteger base, BigInteger power) {
			super();
			this.base = base;
			this.power = power;
		}


		private BigInteger pow(BigInteger base, BigInteger power) {
						
			BigInteger result = BigInteger.ONE;
			
			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {

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
