package com.lix;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import com.lix.accounts.Checking;
import com.lix.accounts.Credit;
import com.lix.accounts.Saving;
import com.lix.enums.Transactions;


public class MainClassBank {

	static Bank myBank = new Bank();
	static String pattern = "yyyy-MM-dd";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	

	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		int optionn = 0;
		
		while (optionn != 7) {
			int selected = menuSelection(s);
			optionn = selected;
			try {
				switch (selected) {
				case 1:
					Person newPerson = personEntriesCreation(s);
					myBank.addPerson(newPerson);
					
					break;

				case 2:
					createAccount(s);
					
					break;

				case 3:
					addOperation(s);
					
					break;
					
				case 4:
					System.out.println("Enter id Person to consult Accounts Status");
					int idp = s.nextInt();
					validatePersonExist(idp);
					List<Status> status = myBank.AccountStatus(idp);
					printStatus(status);
					
					break;
				case 5:
					System.out.println("Clients with more debts");
					printNames(myBank.clientsMostDebt());
					
					break;
				case 6:
					System.out.println("Client with more Money:");
					printNames(myBank.clientMostMoney());
					
					break;

				case 7:
					System.out.close();
					break;

				default:
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		s.close();
	}
	
	public static void printNames(List<Person> personList){
		for (int i = 0; i < personList.size(); i++) {
			System.out.println(personList.get(i).getFirstName());
		}
	}
	
	public static void printStatus(List<Status> myListStatus) {
		String accountType = "";
		for (int i = 0; i < myListStatus.size(); i++) {
			if(myListStatus.get(i).getAccount() instanceof Saving)
				accountType = "Saving";
			else
				if(myListStatus.get(i).getAccount() instanceof Checking)
					accountType = "Checking";
				else
					if(myListStatus.get(i).getAccount() instanceof Credit)
						accountType = "Credit";
			System.out.println(accountType + ": " + myListStatus.get(i).getAmount());
		}
		
	}
	
	public static int menuSelection(Scanner s){
		System.out.println();
		System.out.println("Enter the number of the operation to do:");
		System.out.println("1- Add person");
		System.out.println("2- Create an account to a person");
		System.out.println("3- Create operations in account");
		System.out.println("4- Consult Accouns Status");
		System.out.println("5- See clients with more debts");
		System.out.println("6- See clients with more Money");
		System.out.println("7- Exit");
		int option = s.nextInt();
		return option;
	}
	
    public static Person personEntriesCreation(Scanner s) throws Exception{
		System.out.println("Enter Person ID");
		int id = s.nextInt();
		//checking if the id already exist in the list
		for (int i = 0; i < myBank.personList.size(); i++) {
			if(myBank.personList.get(i).getId() == id)
				throw new Exception("Already exist a person in the system with same id");
		}

		System.out.println("Enter First Name");
		s.nextLine();
		String name = s.nextLine();
		System.out.println("Enter Last Name");
		String lastName = s.nextLine();
		System.out.println("Enter Date of Birth yyyy-MM-dd");
		String date = s.nextLine();
		
		Date dateBirth = simpleDateFormat.parse(date);
		Person p = new Person(id, name, lastName, dateBirth);
		return p;
	}
	
	public static void createAccount(Scanner s) throws Exception{
		System.out.println("Enter id person to create account");
		int idPerson = s.nextInt();
		validatePersonExist(idPerson);
		int accTypeSelected = selectAccType(s);
		//check if the account exist already for that person
		validateAccountExist(idPerson, accTypeSelected);
		
		
		int limitCC = -1;
		if (accTypeSelected == 3) {
			System.out.println("Enter limit amount for your Credit Account");
			limitCC = s.nextInt();
		}
		myBank.createAccount(idPerson, accTypeSelected, limitCC);

	}
	
	public static int selectAccType(Scanner s){
		System.out.println("Select account type:");
		System.out.println("1-Saving");
		System.out.println("2-Checking");
		System.out.println("3-Credit");
		int accountTypSelected = s.nextInt();
		return accountTypSelected;
	}
	
	
	public static void addOperation(Scanner s) throws Exception{
		System.out.println("Enter id person");
		int myIdPerson = s.nextInt();
		validatePersonExist(myIdPerson);
		int selection = selectAccType(s);
		validateAccountNoExist(myIdPerson, selection);
		
		System.out.println("Select the transaction to perform");
		System.out.println("1-DEPOSIT");
		System.out.println("2-WITHDRAWALS");
		int trans = s.nextInt();

		Transactions kindOfTransaction;
		if (trans == 1) {
			kindOfTransaction = Transactions.DEPOSIT;
		} else {
			kindOfTransaction = Transactions.WITHDRAWALS;
		}

		System.out.println("Enter amount for this operation");
		double amount = s.nextDouble();

		System.out.println("Enter transaction description");
		s.nextLine();
		String descrip = s.nextLine();
		
		System.out.println("Enter Transaction Date in format yyyy-MM-dd");
		String transDate = s.nextLine();
		
		Date myTransactionDate = simpleDateFormat.parse(transDate);
		
		myBank.addOperationToAccount(myIdPerson, selection, kindOfTransaction,amount, descrip, myTransactionDate);
		
	}
	
	//Some Validations
	public static void validatePersonExist(int idPerson) throws Exception{
		boolean flag = false;
		for (int i = 0; i < myBank.personList.size(); i++) {
			if(myBank.personList.get(i).getId() == idPerson){
				flag = true;
				break;
			}
		}
		if(!flag)
			throw new Exception("Person does not exist");
	}
	public static void validateAccountExist(int idPerson, int accTypeSelected) throws Exception{
    	for (int i = 0; i < myBank.personList.size(); i++) {
			if(myBank.personList.get(i).getId() == idPerson)
				for (int j = 0; j < myBank.personList.get(i).getAccounts().size(); j++) {
					if((myBank.personList.get(i).getAccounts().get(j) instanceof Credit && accTypeSelected == 3)||(myBank.personList.get(i).getAccounts().get(j) instanceof Saving && accTypeSelected == 1)||(myBank.personList.get(i).getAccounts().get(j) instanceof Checking && accTypeSelected == 2))
						throw new Exception("Account already exist for this person");
				}
		}
    }
	
	
	public static void validateAccountNoExist(int idPerson, int accTypeSelected) throws Exception{
    	for (int i = 0; i < myBank.personList.size(); i++) {
    		boolean flag = false;
			if(myBank.personList.get(i).getId() == idPerson)
				for (int j = 0; j < myBank.personList.get(i).getAccounts().size(); j++) {
					if((myBank.personList.get(i).getAccounts().get(j) instanceof Credit && accTypeSelected == 3)||(myBank.personList.get(i).getAccounts().get(j) instanceof Saving && accTypeSelected == 1)||(myBank.personList.get(i).getAccounts().get(j) instanceof Checking && accTypeSelected == 2)){
						flag = true;
					break;
					}
				}
			if(!flag)
				throw new Exception("Account does not exist for this person");
		}
    }

}
