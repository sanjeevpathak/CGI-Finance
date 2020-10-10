package com.cgi.table;

public class AccountTable {
	private String  accountID;
	private double accountBalance;
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	@Override
	public String toString() {
		return "AccountTable [accountID=" + accountID + ", accountBalance=" + accountBalance + "]";
	}
}
