package com.lix;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lix.accounts.Account;
import com.lix.accounts.Checking;
import com.lix.accounts.Credit;
import com.lix.accounts.Saving;
import com.lix.enums.Transactions;

public class Bank {
	List<Person> personList = new ArrayList<>();

	public void addPerson(Person pEntry) throws Exception {
		//boolean flag = false;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getId() == pEntry.getId()) {
				//flag = true;
				throw new Exception("Already exist a person in the system with same id");
			}
		}
		//if (!flag)
			personList.add(pEntry);
	}

	public void createAccount(int idPerson, int accTypeSelectedparam, int ccLimit) throws Exception {
		boolean flag = false;
		//boolean flag2 = false;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getId() == idPerson) {
				flag = true;
				List<Account> listAccountPerson = personList.get(i).getAccounts();
				for (int j = 0; j < listAccountPerson.size(); j++) {
					if (  ((listAccountPerson.get(j) instanceof Saving) && (accTypeSelectedparam == 1)) || ((listAccountPerson.get(j) instanceof Checking) && (accTypeSelectedparam == 2)) || ((listAccountPerson.get(j) instanceof Credit) && (accTypeSelectedparam == 3)) ) {// CHECK
						throw new Exception("Already exist this kind of account for this person");
						//flag2 = true;
						//break;
					}
				}
				//if ((!flag2) || (listAccountPerson.size() == 0)) {
					Account newAccount;
					if (accTypeSelectedparam == 1) {
						newAccount = new Saving();
						
					} else if (accTypeSelectedparam == 2) {
						newAccount = new Checking();
					} else {
						newAccount = new Credit(ccLimit);
					}
					personList.get(i).getAccounts().add(newAccount);
					System.out.println("New account was added to Person with id " + idPerson);
				//}
				break;

			}
		}
		if (!flag)
			throw new Exception("Invalid id person");
	}

	public void addOperationToAccount(int idPerson, int selection, Transactions kindTransaction, double myamount, String descrip, Date transacDate) throws Exception {
		boolean flag = false;
		boolean noAcc = false;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getId() == idPerson) {
				flag = true;
				List<Account> mAcc = personList.get(i).getAccounts();
				for (int j = 0; j < mAcc.size(); j++) {
					if ( ((mAcc.get(j) instanceof Saving) && (selection == 1)) || ((mAcc.get(j) instanceof Checking) && (selection == 2)) || ((mAcc.get(j) instanceof Credit) && (selection == 3)) ) {
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
							if(mAcc.get(j) instanceof Credit)
								plusIntetest = mAcc.get(j).getAccoutAmount() -  mAcc.get(j).resultInterest();
							else{
							    plusIntetest = mAcc.get(j).getAccoutAmount() + mAcc.get(j).resultInterest();
							}
							mAcc.get(j).setAccoutAmount(plusIntetest);
						}
						}
						noAcc = true;
						personList.get(i).getAccounts().get(j).addOperations(myamount, kindTransaction,
								descrip, transacDate);
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
					Status s = new Status(personList.get(i).getAccounts().get(j), personList.get(i).getAccounts().get(j).getAccoutAmount());
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
				if(personList.get(i).getAccounts().get(j) instanceof Credit){
					aux.add(personList.get(i));
					break;
				}
			}
		}
		double biggest = 0;
		List<Person> aux2 = new ArrayList<>();
		
		int k = 2;//this is to check the list of person the 2nd time to find people with amount == to biggest
		while(k != 0){
		for (int i = 0; i < aux.size(); i++) {
			for (int j = 0; j < aux.get(i).getAccounts().size(); j++) {
				
				if((aux.get(i).getAccounts().get(j) instanceof Credit) 
						&& ((((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount()) >= biggest) 
						&& (k!=1) && (aux.get(i).getAccounts().get(j).getAccoutAmount() != 0)){
					biggest = ((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount();
					//p = aux.get(i);
					break;
				}
				else
					if((aux.get(i).getAccounts().get(j) instanceof Credit) && ((((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount()) == biggest) 
							&& (k==1) && (aux.get(i).getAccounts().get(j).getAccoutAmount() != 0)){
						aux2.add(aux.get(i));
					}
				
			}
		}
		
		k--;
		
	}
		if(aux2.size() == 0)
			throw new Exception("Any registered client has Credit account or they does not have any debt");
		return aux2;
		
		
	}
	
	/*public List<Person> clientsMostDebt() throws Exception{
		List<Person> aux = new ArrayList<>();
		for (int i = 0; i < personList.size(); i++) {
			for (int j = 0; j < personList.get(i).getAccounts().size(); j++) {
				if(personList.get(i).getAccounts().get(j) instanceof Credit){
					aux.add(personList.get(i));
					break;
				}
			}
		}
		double biggest = 0;
		Person p = new Person();
		if(aux.size()!=0){	
		for (int i = 0; i < aux.size(); i++) {
			for (int j = 0; j < aux.get(i).getAccounts().size(); j++) {
				if((aux.get(i).getAccounts().get(j) instanceof Credit) 
						&& ((((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount()) >= biggest) 
						&& (aux.get(i).getAccounts().get(j).getAccoutAmount() != 0)){
					biggest = ((Credit)(aux.get(i).getAccounts().get(j))).getLimit() - aux.get(i).getAccounts().get(j).getAccoutAmount();
					p = aux.get(i);
					break;
				}	
			}
		}
		List<Person> result = new ArrayList<>();
		if(biggest != 0)
			result.add(p);
		else
			throw new Exception("CLients with CC may not have perform any transaction");
		}
		else
			throw new Exception("Any registered client has Credit account or they does not have any debt");
			return
	}*/
	
	public List<Person> clientMostMoney(){
		double sum = 0;
		List<Person> mypersonList = new ArrayList<>();
		List<Person> personListResult = new ArrayList<>();
		List<Double> sumList = new ArrayList<>();
		for (int i = 0; i < personList.size(); i++) {
			sum = 0;
			for (int j = 0; j < personList.get(i).getAccounts().size(); j++) {
				if((personList.get(i).getAccounts().get(j) instanceof Checking) ||(personList.get(i).getAccounts().get(j) instanceof Saving)){
					sum += personList.get(i).getAccounts().get(j).getAccoutAmount();
				}
			}
			if(sum != 0){
				mypersonList.add(personList.get(i));
				sumList.add(sum);
			}
				
		}
		double biggest = 0;
		for (int i = 0; i < sumList.size(); i++) {
			if(sumList.get(i) >= biggest){
				biggest = sumList.get(i);
			}
		}
		
		for (int i = 0; i < mypersonList.size(); i++) {
			if(sumList.get(i) == biggest)
				personListResult.add(mypersonList.get(i));
		}
		return personListResult;
	}
}
