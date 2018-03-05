package com.lix;

import java.util.List;

public class Saving extends Account{

	
	
	
	public Saving(List<Operations> operations,double interest, double accoutAmount){
		super(operations, interest,  accoutAmount);
		interest = 10;
	}
	public Saving(double interest, double accoutAmount, accontType accType) {
		super(interest, accoutAmount, accType);
		interest = 10;
	}

	@Override
	public double resultInterest() {
		double interestSaving = (accoutAmount * interest)/100;
		return interestSaving;
	}
}
