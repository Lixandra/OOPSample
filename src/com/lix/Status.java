package com.lix;

import com.lix.Account.accontType;

public class Status {

	private accontType myAccountType;
	private double amount;
	
	
	public accontType getMyAccountType() {
		return myAccountType;
	}


	public void setMyAccountType(accontType myAccountType) {
		this.myAccountType = myAccountType;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Status (accontType myAccountType,double amount){
		this.myAccountType = myAccountType;
		this.amount = amount;
	}
	
}
