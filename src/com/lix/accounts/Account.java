package com.lix.accounts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lix.Operations;
import com.lix.enums.Transactions;

public abstract class Account {

	/*public enum accontType {
		CHECKING, SAVING, CREDIT
	}*/

	protected List<Operations> operations = new ArrayList<>();
	protected double interest;
	protected double accoutAmount;
	
	
	public Account(double interest) {
		this.interest = interest;
		this.accoutAmount = 0;
	}

	
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
	
	public double getAccoutAmount() {
		return accoutAmount;
	}

	public void setAccoutAmount(double accoutAmount) {
		this.accoutAmount = accoutAmount;
	}

	public abstract double resultInterest();

	public void addOperations(double amount, Transactions transaction, String transDescription, Date transacDate) throws Exception {
		
			if (transaction == Transactions.DEPOSIT)
				accoutAmount += amount;
			else {
				if (transaction == Transactions.WITHDRAWALS) {
					if (accoutAmount < amount)
						throw new Exception("There are no enoung money in the account");
					else
						accoutAmount -= amount;
				}
			}
			// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			//Date date = new Date();

			Operations op = new Operations(transacDate, amount, transaction, transDescription);
			operations.add(op);
		
	}
}
