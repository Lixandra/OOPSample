package com.lix;

import java.util.ArrayList;
import java.util.List;

public class Account {

	protected List<Operations> operations = new ArrayList<>();
	protected double interest;
	public List<Operations> getOperations() {
		return operations;
	}
	public void setOperations(List<Operations> operations) {
		this.operations = operations;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public Account(List<Operations> operations,double interest){
		this.operations = operations;
		this.interest = interest;
	}
}
