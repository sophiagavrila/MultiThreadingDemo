package com.example.D;

public class A_InterruptionDemo {

	public static void main(String[] args) {
		
		// ====== STEP 2 =======
	
		Thread blockingThread = new Thread(new BlockingTask());
		
		blockingThread.start(); /*
								 * if we just start it, you'll notice that the thread
								 * Is sleeping, and running in the background. (Open in Debug mode).
								 * 
								 * The entire app is waiting on the blocking app to finish,
								 * even though the main thread is done...
								 * 
								 * As soon as we call interrupt(), it ends immediately.
								 */
		blockingThread.interrupt();
		// This method will immediately end the thread because
		// we have the logic (in the form of an exception) to handle it).
		
		
		/*
		 * ==========================================
		 * ======= This is an example of ============
		 * ===== interrupting a thread because ======
		 * === it's still running in the background =
		 * ==========================================
		 */

	}
	
	private static class BlockingTask implements Runnable {
		
		
		// ====== STEP 1 =======
		
		@Override
		public void run() {
			// do things
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Exiting blocking Thread");
			}  /*
									* Sleep method explicitly declared to throw interruptedException
									* Which we are forces to handle.
									* 
									* This InterruptedException error is thrown when the thread is 
									* interrrupted externally.
									*/
		}
	}

}
