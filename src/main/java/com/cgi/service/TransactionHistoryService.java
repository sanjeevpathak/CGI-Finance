package com.cgi.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cgi.controller.AccountController;
import com.cgi.table.TransactionTable;

@Service
public class TransactionHistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryService.class);
	
	private static Map<String, List<TransactionTable>> historyRepositiry = new HashMap<String, List<TransactionTable>>();

	public void addHistory(String accountId, double balance, String customerName) {
		TransactionTable transactionInfo = new TransactionTable();
		transactionInfo.setAccountId(accountId);
		transactionInfo.setBalanceChange(balance);
		transactionInfo.setCustomerName(customerName);
		transactionInfo.setTransactionTimestump(new Timestamp(System.currentTimeMillis()));
		if (historyRepositiry.containsKey(accountId)) {
			List<TransactionTable> transactionhistory = historyRepositiry.get(accountId);
			transactionhistory.add(transactionInfo);
			LOGGER.info("history added for the account id{} "+accountId);
			historyRepositiry.put(accountId, transactionhistory);
			LOGGER.info("history details:  "+transactionhistory);
		} else {
			ArrayList<TransactionTable> newTransactionhistory = new ArrayList<TransactionTable>();
			newTransactionhistory.add(transactionInfo);
			LOGGER.info("history added for the account id{} "+accountId);
			historyRepositiry.put(accountId, newTransactionhistory);
			LOGGER.info("history details:  "+newTransactionhistory);
		}

	}

	public List<TransactionTable> getTranshistorybyAccountId(String AccountId) {
			
		return historyRepositiry.get(AccountId).stream()
				.sorted(Comparator.comparing(TransactionTable::getTransactionTimestump).reversed()).collect(Collectors.toList());
	}

	public static Map<String, List<TransactionTable>> getHistoryRepositiry() {
		return historyRepositiry;
	}

	public static void setHistoryRepositiry(Map<String, List<TransactionTable>> historyRepositiry) {
		TransactionHistoryService.historyRepositiry = historyRepositiry;
	}
}
