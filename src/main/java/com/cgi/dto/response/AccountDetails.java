package com.cgi.dto.response;

public class AccountDetails {
	private String accountID;
	private String customerName;
	private Double accountBalance;

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

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "AccountDetails [accountID=" + accountID + ", customerName=" + customerName + ", accountBalance="
				+ accountBalance + "]";
	}
}
