package com.cgi.dto.response;

import java.util.Date;

public class TransactionHistory {
	private String accountID;
	private String customerName;
	private double balanceChange;
	private Date timestamp;

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getBalanceChange() {
		return balanceChange;
	}

	public void setBalanceChange(double balanceChange) {
		this.balanceChange = balanceChange;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
