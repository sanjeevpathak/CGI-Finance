package com.cgi.table;

import java.sql.Timestamp;

public class TransactionTable {
	
	private String accountId;
	private String customerName;
	private Double balanceChange;
	private Timestamp transactionTimestump;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Double getBalanceChange() {
		return balanceChange;
	}
	public void setBalanceChange(Double balanceChange) {
		this.balanceChange = balanceChange;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Timestamp getTransactionTimestump() {
		return transactionTimestump;
	}
	public void setTransactionTimestump(Timestamp transactionTimestump) {
		this.transactionTimestump = transactionTimestump;
	}
	@Override
	public String toString() {
		return "TransactionTable [accountId=" + accountId + ", customerName=" + customerName + ", balanceChange="
				+ balanceChange + ", transactionTimestump=" + transactionTimestump + "]";
	}

}
