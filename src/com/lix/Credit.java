package com.lix;

import java.util.Date;
import java.util.List;

public class Credit extends Account {
	private int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Credit(List<Operations> operations, double interest, double accoutAmount, int limit) {
		super(operations, interest, accoutAmount);
		this.limit = limit;
		interest = 20;
	}

	public Credit(double interest, double accoutAmount, accontType accType, int limit) {
		super(interest, accoutAmount, accType);
		this.limit = limit;
		interest = 20;
	}

	@Override
	public double resultInterest() {
		double interestCredit = (accoutAmount * interest) / 100;
		return interestCredit;
	}

	@Override
	public void addOperations(double amount, Transactions transaction, String transDescription) throws Exception{
		
			if(transaction == Transactions.DEPOSIT)//accoutAmount in CC is how  much have been wasted
				accoutAmount += amount;
			else{
				if((transaction == Transactions.WITHDRAWALS) &&  (((accoutAmount == 0) && (accoutAmount + amount > limit)) || ((accoutAmount!=0 )&& (accoutAmount < amount)))){// when pay with CC
					throw new Exception("The transaction amount overpass the account limit");
				}
				else if(accoutAmount == 0){
					accoutAmount = limit - amount;
				}
				else
					accoutAmount = accoutAmount - amount;
					
			}
			
			Date date = new Date();
			Operations op = new Operations(date, amount, transaction, transDescription);
			operations.add(op);
			
	}

}
