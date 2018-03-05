package com.lix;

import java.util.List;

public class Checking extends Account {

	
	
	public Checking(List<Operations> operations, double interest, double accoutAmount) {
		super(operations, interest, accoutAmount);
		interest = 5;
	}

	public Checking(double interest, double accoutAmount,accontType accType) {
		super(interest, accoutAmount, accType);
		interest = 5;
	}

	@Override
	public double resultInterest() {
		double interestChecking = (accoutAmount * interest) / 100;
		return interestChecking;
	}
}
