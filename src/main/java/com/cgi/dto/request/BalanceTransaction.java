package com.cgi.dto.request;

import org.springframework.lang.NonNull;

public class BalanceTransaction {

	@NonNull
	private String accountID;
	@NonNull
	private double balanceChange;

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public double getBalanceChange() {
		return balanceChange;
	}

	public void setBalanceChange(double balanceChange) {
		this.balanceChange = balanceChange;
	}

}
