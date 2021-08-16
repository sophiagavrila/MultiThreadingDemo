package com.example.A;

public class A_ThreadCreation {
	
	/*
	 * =================================================================
	 * ====== THERE ARE TWO MAIN WAYS TO CREATE THREADS ================
	 * =================================================================
	 * 
	 * 		(1) Implement Runnable interface and pass to a new Thread object
	 * 			(as you see below)
	 * 
	 * 		(2) Extend Thread class, and create an object of that class. 
	 * 
	 * ================== Both are equally correct! ====================
	 */

	public static void main(String[] args) throws InterruptedException {

		/*
		 * All Thread's related properties and methods are 
		 * within the Thread class in the JDK.
		 */

		// Thread object is empty by default so we need to pass an object
		// of the Runnable class into its constructor.
		Thread thread = new Thread(new Runnable() {

			// Since we are implementing the Runnable interface, we must override
			// the action to be taken when "run" is called

			@Override
			public void run() {
				// This is the code that will be run on the OS
				// as soon as its scheduled by the OS... The OS will call the .start() method

				System.out.println("We are now in thread " + Thread.currentThread().getName()); // *** SET BREAKPOINT HERE (for debugging) ***
				
				// On the SECOND RUNNING of the thread, go ahead and 
				// print the # priority of the thread.
				System.out.println("Current thread priority is: " + Thread.currentThread().getPriority());

			}

		});
		
		// To set the thread's name:
		thread.setName("New Worker Thread");
		
		/*
		 * ====== ON THE SECOND RUNNING OF THE PROGRAM, ADD BELOW ====== 
		 * 
		 * Remember that when Threads are scheduled by the OS,
		 * The OS MAINTAINS DYNAMIC PRIORITY...we can do so by programmatically 
		 * setting the priority on the thread object.
		 */
		thread.setPriority(Thread.MAX_PRIORITY); // you will see that the thread's priotiry is 10.
		

		// Thread Class has a few useful methods...
		System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting new thread."); // *** SET BREAKPOINT HERE ***

		// == TALK ABOUT THIS BEFORE ALL OTHER METHODS ==
		// .START() instructs the JVM to create a new thread and pass it to the OS
		thread.start();

		System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting new thread."); // *** SET BREAKPOINT HERE ***

		// Instructs the OS to NOT schedule the thread during this time at which
		// the thread in question is NOT consuming any CPU.
		Thread.sleep(10000); // puts the Thread to sleep.  

	}
}
