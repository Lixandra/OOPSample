package com.lix;

import java.util.List;

public class Credit extends Account{
 private int limit;
 
 public Credit(int limit, List<Operations> operations,double interest){
		super(operations, interest);
		this.limit = limit;
	}
}
