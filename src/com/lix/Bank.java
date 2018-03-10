package com.lix;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lix.Account.accontType;

public class Bank {
	List<Person> personList = new ArrayList<>();

	public void addPerson(Person pEntry) {
		boolean flag = false;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getId() == pEntry.getId()) {
				System.out.println("Already exist a person in the system with same id");
				flag = true;
				break;
			}
		}
		if (!flag)
			personList.add(pEntry);
	}

	public void createAccount(int idPerson, Account a) {
		boolean flag = false;
		boolean flag2 = false;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getId() == idPerson) {
				flag = true;
				List<Account> listAccountPerson = personList.get(i).getAccounts();
				for (int j = 0; j < listAccountPerson.size(); j++) {
					if (listAccountPerson.get(j).getAccType().equals(a.getAccType())) {// CHECK
						System.out.println("Already exist this kind of account for this person");
						flag2 = true;
						break;
					}
				}
				if ((!flag2) || (listAccountPerson.size() == 0)) {
					Account newAccount;
					if (a.accType == accontType.SAVING) {
						newAccount = new Saving(a.getInterest(), a.getAccoutAmount(), a.getAccType());
						// personList.get(i).getAccounts().add(newAccount);
					} else if (a.accType == accontType.CHECKING) {
						newAccount = new Checking(a.getInterest(), a.getAccoutAmount(), a.getAccType());
					} else {
						int limitCC = ((Credit) a).getLimit();
						//a.setAccoutAmount(limitCC);
						newAccount = new Credit(a.getInterest(), a.getAccoutAmount(), a.getAccType(), limitCC);

					}
					personList.get(i).getAccounts().add(newAccount);
					System.out.println("New account was added to Person with id " + idPerson);
				}
				break;

			}
		}
		if (!flag)
			System.out.println("Invalid id person");
	}

	public void addOperationToAccount(int idPerson, accontType myAccType, double myamount, Transactions myTransaction,
			String myTransDescription, Date transacDate) throws Exception {
		
		boolean flag = false;
		boolean noAcc = false;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getId() == idPerson) {
				flag = true;
				List<Account> mAcc = personList.get(i).getAccounts();
				for (int j = 0; j < mAcc.size(); j++) {
					if (mAcc.get(j).getAccType().equals(myAccType)) {
						//applying interest
						Calendar myCalendar = Calendar.getInstance();
						myCalendar.setTime(transacDate);
						int calendarMonth = myCalendar.get(Calendar.MONTH);
						
						int pos = mAcc.get(j).getOperations().size() - 1;
						if(pos!= -1){
						Date datePreviousTransac = mAcc.get(j).getOperations().get(pos).getTransactionDate();
						myCalendar.setTime(datePreviousTransac);
						int monthPreviousTrans = myCalendar.get(Calendar.MONTH);
						
						if(calendarMonth != monthPreviousTrans){
							double plusIntetest;
							if(mAcc.get(j).getAccType() == accontType.CREDIT)
								 plusIntetest = mAcc.get(j).getAccoutAmount() -  mAcc.get(j).resultInterest();
							else{
							    plusIntetest = mAcc.get(j).getAccoutAmount() + mAcc.get(j).resultInterest();
							}
							mAcc.get(j).setAccoutAmount(plusIntetest);
						}
						}
						noAcc = true;
						personList.get(i).getAccounts().get(j).addOperations(myamount, myTransaction,
								myTransDescription, transacDate);
						System.out.println("Operation added");
						break;
					}

				}
				if (!noAcc) {
					throw new Exception("The account does not exist");
					
				}
			}

		}
		if (!flag)
			throw new Exception("There are not person with specified ID");

	}

	public List<Status> AccountStatus(int idPerson) throws Exception{
		List<Status> myAccStatusList = new ArrayList<>();
		for (int i = 0; i < personList.size(); i++) {
			int accSize = personList.get(i).getAccounts().size();
			if((personList.get(i).getId() == idPerson) && (accSize != 0)){
				for (int j = 0; j < accSize; j++) {
					Status s = new Status(personList.get(i).getAccounts().get(j).getAccType(), personList.get(i).getAccounts().get(j).getAccoutAmount());
					myAccStatusList.add(s);
				}
			}
		}
		if(myAccStatusList.size() == 0)
			throw new Exception("Account Status not available");
			else
				return myAccStatusList;
	}

	public List<Person> clientsMostDebt() throws Exception{
		List<Person> aux = new ArrayList<>();
		for (int i = 0; i < personList.size(); i++) {
			for (int j = 0; j < personList.get(i).getAccounts().size(); j++) {
				if(personList.get(i).getAccounts().get(j).getAccType() == accontType.CREDIT)
					aux.add(personList.get(i));
			}
		}
		double biggest = 0;
		List<Person> aux2 = new ArrayList<>();
		//Person p = new Person();
		int k = 2;
		while(k != 0){
		for (int i = 0; i < aux.size(); i++) {
			for (int j = 0; j < aux.get(i).getAccounts().size(); j++) {
				
				if((aux.get(i).getAccounts().get(j).getAccType() == accontType.CREDIT) && ((((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount()) >= biggest) && (k!=1)){
					biggest = ((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount();
					//p = aux.get(i);
					break;
				}
				else
					if((aux.get(i).getAccounts().get(j).getAccType() == accontType.CREDIT) && ((((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount()) == biggest) && (k==1)){
						aux2.add(aux.get(i));
					}
				
			}
		}
		//if(k==2){
		//aux2.add(p);
		//}
		
		k--;
		
	}
		if(aux2.size() == 0)
			throw new Exception("Any registered client has Credit account or they does not have any debt");
		return aux2;
		
		
	}
	
	public List<Person> clientMostMoney(){
		double sum = 0;
		//double sumAux = 0;
		List<Person> mypersonList = new ArrayList<>();
		List<Person> personListResult = new ArrayList<>();
		List<Double> sumList = new ArrayList<>();
		for (int i = 0; i < personList.size(); i++) {
			sum = 0;
			for (int j = 0; j < personList.get(i).getAccounts().size(); j++) {
				if((personList.get(i).getAccounts().get(j).getAccType() == accontType.CHECKING) ||(personList.get(i).getAccounts().get(j).getAccType() == accontType.SAVING)){
					sum += personList.get(i).getAccounts().get(j).getAccoutAmount();
				}
			}
			if(sum != 0){
				mypersonList.add(personList.get(i));
				sumList.add(sum);
			}
				
		}
		//int pos = -1;
		double biggest = 0;
		for (int i = 0; i < sumList.size(); i++) {
			if(sumList.get(i) >= biggest){
				biggest = sumList.get(i);
				//pos = i;
			}
		}
		
		for (int i = 0; i < mypersonList.size(); i++) {
			if(sumList.get(i) == biggest)
				personListResult.add(mypersonList.get(i));
		}
		return personListResult;
	}
}
