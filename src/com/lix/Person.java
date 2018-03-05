package com.lix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {
	private List<Account> accounts = new ArrayList<>();
	private int id;
	private String firstName;
	private String lastName;
	private Date dateBirth;

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public Person(List<Account> accounts, int id, String firstName, String lastName, Date dateBirth) {
		this.accounts = accounts;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateBirth = dateBirth;
	}
	public Person(int id, String firstName, String lastName, Date dateBirth){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateBirth = dateBirth;
	}

}
