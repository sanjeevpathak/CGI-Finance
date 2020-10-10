package com.cgi.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.cgi.dto.request.BalanceTransaction;
import com.cgi.dto.request.Customer;
import com.cgi.service.AccountService;
import com.cgi.service.CustomerService;
import com.cgi.service.TransactionHistoryService;
import com.cgi.table.AccountTable;
import com.cgi.table.CustomerTable;
import com.cgi.table.TransactionTable;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionHistoryServiceTest {

	@Autowired
	private TransactionHistoryService history;

	private Customer customer = new Customer();

	@BeforeEach
	public void setup() {

		customer.setAccountID("12345");
		customer.setCustomerName("Sanjeev");
		customer.setInitialCredit(10.0);

	}

	@Test
	public void addHistoryTest() {

		history.addHistory(customer.getAccountID(), customer.getInitialCredit(), customer.getCustomerName());
		List<TransactionTable> transtion = history.getHistoryRepositiry().get(customer.getAccountID());
		assertEquals(customer.getAccountID(), transtion.get(0).getAccountId());
		assertEquals(customer.getCustomerName(), transtion.get(0).getCustomerName());
	}

}
