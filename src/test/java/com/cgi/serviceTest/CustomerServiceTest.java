package com.cgi.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.cgi.dto.request.BalanceTransaction;
import com.cgi.dto.request.Customer;
import com.cgi.service.AccountService;
import com.cgi.service.CustomerService;
import com.cgi.table.AccountTable;
import com.cgi.table.CustomerTable;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerServiceTest {
	@MockBean
	private AccountService accountService;
	@SpyBean
	private CustomerService customerRepository;

	private AccountTable account = new AccountTable();
	private CustomerTable customerTable = new CustomerTable();
	private BalanceTransaction transaction = new BalanceTransaction();
	private Customer customer = new Customer();

	@BeforeEach
	public void setup() {

		customerTable = new CustomerTable();
		customerTable.setAccountId("1234");
		customerTable.setCustomerName("Sanjeev");
		customer.setAccountID("12345");
		customer.setCustomerName("Sanjeev");
		customer.setInitialCredit(10.0);

		accountService.getAccountRepositoryData().put(account.getAccountID(), account);
		customerRepository.getCustomerRepositoryData().put(customerTable.getAccountId(), customerTable);

	}

	@Test
	public void savecustomerTest() {
		Customer customerResult = customerRepository.save(customer);
		assertEquals("12345", customerResult.getAccountID());
		assertEquals("Sanjeev", customerResult.getCustomerName());
		assertEquals(10.0, customerResult.getInitialCredit());

	}

	@Test
	public void savecustomerTest_when_customer_exist() {
		customer.setAccountID("1234");
		Customer customerResult = customerRepository.save(customer);
		assertNull(customerResult);

	}

	@Test
	public void findCustomerByAccountIDTest() {
		customer.setAccountID("1234");
		CustomerTable actual = customerRepository.findCustomerByAccountID(customer.getAccountID());
		assertEquals(customerTable, actual);
	}

}
