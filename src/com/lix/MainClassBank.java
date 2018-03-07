package com.lix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.lix.Account.accontType;

public class MainClassBank {

	static Bank myBank = new Bank();

	public static void printStatus(List<Status> myListStatus) {
		for (int i = 0; i < myListStatus.size(); i++) {
			System.out.println(myListStatus.get(i).getMyAccountType() + ": " + myListStatus.get(i).getAmount());
		}
		
	}
	

	
	public static void main(String[] args) throws ParseException {
		Scanner s = new Scanner(System.in);
		int optionn = 0;
		while (optionn != 7) {

			System.out.println("Enter the number of the operation to do:");
			System.out.println("1- Add person");
			System.out.println("2- Create an account to a person");
			System.out.println("3- Create operations in account");
			System.out.println("4- Consult Accouns Status");
			System.out.println("5- See clients with more debts");
			System.out.println("6- See clients with more Money");
			System.out.println("7- Exit");
			int option = s.nextInt();
			optionn = option;
			try {
				switch (option) {
				case 1:
					System.out.println("Enter Person ID");
					int id = s.nextInt();
					System.out.println("Enter First Name");
					s.nextLine();
					String name = s.nextLine();
					System.out.println("Enter Last Name");
					String lastName = s.nextLine();
					System.out.println("Enter Date of Birth yyyy-MM-dd");
					String date = s.nextLine();
					String pattern = "yyyy-MM-dd";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					Date dateBirth = simpleDateFormat.parse(date);
					Person p = new Person(id, name, lastName, dateBirth);
					myBank.addPerson(p);
					break;

				case 2:
					System.out.println("Enter id person to create account");
					int idPerson = s.nextInt();
					System.out.println("Select account type:");
					System.out.println("1-Saving");
					System.out.println("2-Checking");
					System.out.println("3-Credit");
					Account myNewAccount;
					int accountTyp = s.nextInt();
					if (accountTyp == 1) {
						myNewAccount = new Saving(10, 0, accontType.SAVING);
					} else if (accountTyp == 2) {
						myNewAccount = new Checking(5, 0, accontType.CHECKING);
					} else {
						System.out.println("Enter limit amount for your Credit Account");
						int limitCC = s.nextInt();
						myNewAccount = new Credit(20, 0, accontType.CREDIT, limitCC);
					}
					myBank.createAccount(idPerson, myNewAccount);

					break;

				case 3:
					System.out.println("Enter id person");
					int myIdPerson = s.nextInt();
					System.out.println("Select Account type");
					System.out.println("1-Saving");
					System.out.println("2-Checking");
					System.out.println("3-Credit");
					int selection = s.nextInt();

					accontType accT;
					if (selection == 1) {
						accT = accontType.SAVING;
					} else if (selection == 2) {
						accT = accontType.CHECKING;
					} else {
						accT = accontType.CREDIT;
					}

					System.out.println("Select the transaction to perform");
					System.out.println("1-DEPOSIT");
					System.out.println("2-WITHDRAWALS");
					int trans = s.nextInt();

					Transactions transactionACC;
					if (trans == 1) {
						transactionACC = Transactions.DEPOSIT;
					} else {
						transactionACC = Transactions.WITHDRAWALS;
					}

					System.out.println("Enter amount for this operation");
					double amount = s.nextDouble();

					System.out.println("Enter transaction description");
					s.nextLine();
					String descrip = s.nextLine();
					myBank.addOperationToAccount(myIdPerson, accT, amount, transactionACC, descrip);
					break;
					
				case 4:
					System.out.println("Enter id Person to consult Accounts Status");
					int idp = s.nextInt();
					List<Status> status = myBank.AccountStatus(idp);
					printStatus(status);
					
					break;
				case 5:
					List<Person> pPrint = myBank.clientsMostDebt();
					System.out.println("Clients with more debts");
					for (int i = 0; i < pPrint.size(); i++) {
						System.out.println(pPrint.get(i).getFirstName());
					}
					break;
				case 6:
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

	// System.out.println(myBank.personList.get(0).getAccounts().get(0).getAccoutAmount());
	// System.out.println(myBank.personList.get(0).getAccounts().get(0).getAccType());
	// System.out.println(myBank.personList.get(0).getFirstName());
}
