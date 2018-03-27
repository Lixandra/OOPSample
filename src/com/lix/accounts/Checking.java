package com.lix.accounts;

public class Checking extends Account {

	
	public Checking() {
		super(5);
	}

	@Override
	public double resultInterest() {
		double interestChecking = (accoutAmount * interest) / 100;
		return interestChecking;
	}
}
