package com.cgi.controllerTest;

import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.cgi.dto.request.BalanceTransaction;
import com.cgi.dto.request.Customer;
import com.cgi.dto.response.AccountDetails;
import com.cgi.service.AccountService;
import com.cgi.service.CustomerService;
import com.cgi.service.TransactionHistoryService;
import com.cgi.table.CustomerTable;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountControllerTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private CustomerService customerRepository;
	@MockBean
	private AccountService accountRepository;
	@MockBean
	private TransactionHistoryService transactionHistoryRepository;
	private CustomerTable customer;
	private Customer customerRequest;

	private BalanceTransaction balanceTransaction;

	@LocalServerPort
	int randomServerPort;

	@BeforeEach
	public void setup() {
		customer = new CustomerTable();
		customer.setAccountId("1234");
		customer.setCustomerName("Sanjeev");
		// object initialized for creating new customer
		customerRequest = new Customer();
		customerRequest.setAccountID("123");
		customerRequest.setCustomerName("Sanjeev");
		customerRequest.setInitialCredit(20.3);

		balanceTransaction = new BalanceTransaction();
		balanceTransaction.setAccountID("1234");
		balanceTransaction.setBalanceChange(12.0);

	}

	@Test
	public void getAccountDetailsByAccountIdTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/customer/1234";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		HttpEntity<String> request = new HttpEntity<>(null, headers);
		when(customerRepository.findCustomerByAccountID("1234")).thenReturn(customer);
		when(accountRepository.getAccountBalance("1234")).thenReturn(200.0);
		ResponseEntity<AccountDetails> result = restTemplate.exchange(uri, HttpMethod.GET, request,
				AccountDetails.class);
		Assertions.assertEquals(200, result.getStatusCodeValue());
		Assertions.assertNotNull(result.getBody());
	}

	@Test
	public void createAccountTest() throws Exception {
		when(customerRepository.findCustomerByAccountID("1234")).thenReturn(customer);
		when(customerRepository.save(customerRequest)).thenReturn(customerRequest);
		final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/customer";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		HttpEntity<Customer> request = new HttpEntity<>(customerRequest, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

		Assertions.assertEquals(201, result.getStatusCodeValue());

	}

	@Test
	public void updateAccountBalanceTest() throws Exception {
		when(customerRepository.findCustomerByAccountID("1234")).thenReturn(customer);

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/customer";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		HttpEntity<BalanceTransaction> request = new HttpEntity<>(balanceTransaction, headers);
		restTemplate.put(uri, request);
		// restTemplate.put
		ResponseEntity<Double> result = restTemplate.exchange(uri, HttpMethod.PUT, request, Double.class);
		Assertions.assertNotNull(result);

	}
}
