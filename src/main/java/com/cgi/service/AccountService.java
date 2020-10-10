package com.cgi.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.dto.request.BalanceTransaction;
import com.cgi.exception.InsufficientBalanceException;
import com.cgi.table.AccountTable;

@Service
public class AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
	private static Map<String, AccountTable> accountRepositoryData = new HashMap<String, AccountTable>();
	@Autowired
	TransactionHistoryService history ;
	@Autowired
	CustomerService customerRepository ;

	public void addAccountBalance(String accountId, Double amount) {
		AccountTable account = new AccountTable();
		account.setAccountID(accountId);
		account.setAccountBalance(account.getAccountBalance() + amount);
		accountRepositoryData.put(accountId, account);
		String customerName = customerRepository.findCustomerByAccountID(accountId).getCustomerName();
		history.addHistory(accountId, account.getAccountBalance(), customerName);

	}

	public double updateAccountBalance(BalanceTransaction transaction) {
		String accountId = transaction.getAccountID();
		double amount = transaction.getBalanceChange();

		double currentBalance = accountRepositoryData.get(accountId).getAccountBalance();
		double updatebalance = currentBalance + amount;

		if (updatebalance < 0) {
			LOGGER.error("insufficient balance to process this request "+updatebalance);
			throw new InsufficientBalanceException("insufficient balance to process this request");

		}
		AccountTable account = accountRepositoryData.get(accountId);
		account.setAccountBalance(updatebalance);
		accountRepositoryData.put(accountId, account);
		String customerName = customerRepository.findCustomerByAccountID(accountId).getCustomerName();
		history.addHistory(accountId, updatebalance, customerName);
		LOGGER.info("Updated account balance"+updatebalance);
		return updatebalance;

	}

	public Double getAccountBalance(String accountId) {
		if (accountRepositoryData.containsKey(accountId)) {
			return accountRepositoryData.get(accountId).getAccountBalance();
		}
		return null;
	}

	public static Map<String, AccountTable> getAccountRepositoryData() {
		return accountRepositoryData;
	}

	public static void setAccountRepositoryData(Map<String, AccountTable> accountRepositoryData) {
		AccountService.accountRepositoryData = accountRepositoryData;
	}

}
