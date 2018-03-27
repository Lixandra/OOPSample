package com.lix.accounts;

public class Saving extends Account{

	public Saving() {
		super(10);
	}

	@Override
	public double resultInterest() {
		double interestSaving = (accoutAmount * interest)/100;
		return interestSaving;
	}
}
