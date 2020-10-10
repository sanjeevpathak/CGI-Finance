package com.cgi.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.cgi.dto.request.BalanceTransaction;
import com.cgi.service.AccountService;
import com.cgi.service.CustomerService;
import com.cgi.service.TransactionHistoryService;
import com.cgi.table.AccountTable;
import com.cgi.table.CustomerTable;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class AccountServiceTests {

	@Autowired
	private AccountService accountService;
	@MockBean
	private TransactionHistoryService history;
	@SpyBean
	private CustomerService customerRepository;

	private AccountTable account = new AccountTable();
	private CustomerTable customer = new CustomerTable();
	private BalanceTransaction transaction = new BalanceTransaction();

	@BeforeEach
	public void setup() {

		account.setAccountID("1234");
		account.setAccountBalance(20.0);

		transaction.setAccountID("1234");
		transaction.setBalanceChange(12.0);

		customer = new CustomerTable();
		customer.setAccountId("1234");
		customer.setCustomerName("Sanjeev");

		accountService.getAccountRepositoryData().put(account.getAccountID(), account);
		customerRepository.getCustomerRepositoryData().put(customer.getAccountId(), customer);

	}

	@Test
	public void addAccountBalanceTest() {
		account.setAccountBalance(account.getAccountBalance() + 20);
		assertEquals(40.0, account.getAccountBalance());

	}

	@Test
	public void updateAccountBalanceTest_with_positive_balance() {

		double updateAccountBalance = accountService.updateAccountBalance(transaction);
		assertEquals(32.0, updateAccountBalance);

	}

	@Test
	public void updateAccountBalanceTest_with_negative_balance() {
		transaction.setBalanceChange(-32.0);
		try {
			accountService.updateAccountBalance(transaction);

		} catch (Exception e) {
			assertEquals("insufficient balance to process this request", e.getLocalizedMessage());

		}

	}

	@Test
	public void getAccountBalanceTest_when_account_exist() {
		Double accountBalance = accountService.getAccountBalance("1234");
		assertEquals(20.0, accountBalance);

	}

	@Test
	public void getAccountBalanceTest_when_account_doNot_exist() {
		Double accountBalance = accountService.getAccountBalance("12345");
		assertNull(accountBalance);

	}

}