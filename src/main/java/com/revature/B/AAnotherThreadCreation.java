package com.revature.B;

public class AAnotherThreadCreation {

	/*
	 * This is the second way we create a new thread, which is essentially
	 * scheduling some code to be run at a specific time.
	 */
	
	public static void main(String[] args) {
		
		/*
		 * Instead of creating a generic thread object, we create a NewThread
		 * object (our custom inner class)
		 */
		
		Thread myThread = new NewThread();
		
		myThread.start();
		
	}

	/*
	 * Instead of creating 2 separate objects (one for thread and one for Runnable),
	 * we can directly create a new class that extends Thread.
	 * 
	 * By extending Thread, we are automatically implementing Runnable.
	 */

	private static class NewThread extends Thread {
		
		@Override
		public void run() {
			// Instead of calling the static method Thread.currentThread(), we can just use this.
			System.out.println("Hello from " + this.getName()); // can also access id, etc.
		}
		
		/*
		 * ^^^^^^ THIS IS THE BEST WAY TO CREATE A THREAD ^^^^^^^
		 */

	}
}
