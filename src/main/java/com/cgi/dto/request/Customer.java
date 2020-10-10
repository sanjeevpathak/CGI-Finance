package com.cgi.dto.request;

import org.springframework.lang.NonNull;

public class Customer {
	@NonNull
	private String accountID;
	@NonNull
	private String customerName;
	@NonNull
	private Double initialCredit;

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

	public Double getInitialCredit() {
		return initialCredit;
	}

	public void setInitialCredit(Double initialCredit) {
		this.initialCredit = initialCredit;
	}

	@Override
	public String toString() {
		return "Customer [accountID=" + accountID + ", customerName=" + customerName + ", initialCredit="
				+ initialCredit + "]";
	}

	
}
