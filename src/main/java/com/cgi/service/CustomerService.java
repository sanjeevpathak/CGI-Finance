package com.cgi.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cgi.dto.request.Customer;
import com.cgi.table.CustomerTable;

@Service
public class CustomerService  {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
	private static Map<String, CustomerTable> customerRepositoryData = new HashMap<String, CustomerTable>();

	public Customer save(Customer customer) {
		if (!customerRepositoryData.containsKey(customer.getAccountID())) {
			CustomerTable customerTable= new CustomerTable();
			customerTable.setAccountId(customer.getAccountID());
			customerTable.setCustomerName(customer.getCustomerName());
			customerRepositoryData.put(customer.getAccountID(), customerTable);
			return customer;
		}
		LOGGER.info("customer{} alreday exist " + customer);
		return null;
	}

	
	public CustomerTable findCustomerByAccountID(String accountID) {
		return customerRepositoryData.get(accountID);
	}


	public static Map<String, CustomerTable> getCustomerRepositoryData() {
		return customerRepositoryData;
	}


	public static void setCustomerRepositoryData(Map<String, CustomerTable> customerRepositoryData) {
		CustomerService.customerRepositoryData = customerRepositoryData;
	}
}
