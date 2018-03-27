package com.lix;

import com.lix.accounts.Account;

public class Status {

	//private accontType myAccountType;
	private Account account;
	private double amount;
	
	public Status (Account account,double amount){
		this.account = account;
		this.amount = amount;
	}
	
	
	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}

	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	
	
	
}
