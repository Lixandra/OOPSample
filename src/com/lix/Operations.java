package com.lix;

import java.util.Date;

enum Transactions{DEPOSIT, WITHDRAWALS}
public class Operations {

	private Date transactionDate;
	private double transactionAmount;
	private Transactions transactionType;//check
	private String transactionDescription;
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Transactions getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Transactions transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionDescription() {
		return transactionDescription;
	}
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}
	
	public Operations(Date transactionDate,double transactionAmount, Transactions transactionType, String transactionDescription){
		this.transactionDate = transactionDate;
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
		this.transactionDescription = transactionDescription;
	}
	
	
}
