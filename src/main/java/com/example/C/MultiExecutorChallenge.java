package com.example.C;

import java.util.List;
import java.util.ArrayList;

/*
 * ===========================================================
 * ======= Thread Creation - MultiExecutor Solution =========
 * ===========================================================
 * In this exercise we are going to implement a  MultiExecutor .
 * 
 * The client of this class will create a list of Runnable tasks 
 * and provide that list into MultiExecutor's constructor.
 * 
 * When the client runs the . executeAll(),  the MultiExecutor 
 * will execute all the given tasks.
 * 
 * To take full advantage of our multicore CPU, we would like the 
 * MultiExecutor to execute all the tasks in parallel, 
 * by passing each task to a different thread.
 * ===========================================================
 * ===========================================================
 */

/*
 * =================================
 * ========== Challange ============
 * =================================
 */

public class MultiExecutorChallenge {

	// Add any necessary member variables here

	/*
	 * @param tasks to executed concurrently
	 */
	public MultiExecutorChallenge(List<Runnable> tasks) {
		// Complete your code here
	}

	/**
	 * Starts and executes all the tasks concurrently
	 */
	public void executeAll() {
		// complete your code here
	}
}


/*
 * ==================================
 * ========== Solution A ============
 * ==================================
 */
class SolutionA {

	private final List<Runnable> tasks;

	/*
	 * @param tasks to executed concurrently
	 */
	public SolutionA(List<Runnable> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Executes all the tasks concurrently
	 */
	public void executeAll() {
		List<Thread> threads = new ArrayList<>(tasks.size()); // create an ArrayList the same size as 
															  // current <Runnable> tasks member variable.

		for (Runnable task : tasks) {
			Thread thread = new Thread(task);
			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.start();
		}
	}
}

/*
 * ==================================
 * ========== Solution B ============
 * ==================================
 */
class SolutionB {

	// Add any necessary member variables here
	List<Thread> tasks = new ArrayList<>();

	/*
	 * @param tasks to executed concurrently
	 */
	public SolutionB(List<Runnable> tasks) {
		for (Runnable r : tasks) {
			this.tasks.add(new Thread(r));
		}
	}

	/**
	 * Starts and executes all the tasks concurrently
	 */
	public void executeAll() {

		for (Thread t : tasks) {
			t.start();
		}
	}
}
