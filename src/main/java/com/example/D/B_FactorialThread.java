package com.example.D;

import java.math.BigInteger;

public class B_FactorialThread extends Thread {
	
	private long inputNumber;
	private BigInteger result = BigInteger.ZERO;
	private boolean isFinished = false;
	
	// The constructor takes in the input number
	public B_FactorialThread(long inputNumber) {
		this.inputNumber = inputNumber;
	}
	
	/*
	 *  When the run() method is called, it reutrns the factorial 
	 *  of the inputNumber (entered thru constructor)
	 *  and isFinished is updated to true; 
	 */
	@Override
	public void run() {
		this.result = factorial(inputNumber);
		this.isFinished = true;
	}
	
	// This method is called by run() method to return factorial based on inputNumber
	public BigInteger factorial (long n) {
		
		BigInteger tempResult = BigInteger.ONE;
		
		for (long i = n; i > 0; i--) {
			tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
		} 
		
		return tempResult;
		
	}
	

	// These are both essentially getters.
	public boolean isFinished() {
		return isFinished;
	}
	
	public BigInteger getResult() {
		return result;
	}
	

}
