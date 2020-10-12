package com.cgi.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.dto.request.BalanceTransaction;
import com.cgi.dto.request.Customer;
import com.cgi.dto.response.AccountDetails;
import com.cgi.exception.CustomerAlreadyExistException;
import com.cgi.exception.CustomerNotFoundException;
import com.cgi.service.AccountService;
import com.cgi.service.CustomerService;
import com.cgi.service.TransactionHistoryService;
import com.cgi.table.CustomerTable;
import com.cgi.table.TransactionTable;

@RestController
@RequestMapping("api/v1")
public class AccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	CustomerService customerRepository ;
	@Autowired
	AccountService accountRepository; 
	@Autowired
	TransactionHistoryService historyRepository ;
	
	
	/*
	 * @api{get}
	 * 
	 * @apiVersion 1.0.0
	 * 
	 * @apiDescription provide the information of already existing customer
	 * 
	 * @apiDescription
	 * 
	 * @apiUri http://localhost:8080/api/v1//customer/{accountID}
	 *  onSuccess {
	 * "accountID": "2345", "customerName": "Pathak", "accountBalance": 101 }
	 *  when customer information does not exist
	 * 
	 * { "timestamp": "2020-10-12T05:48:41.690+00:00", "message":
	 * "No customer exist with Id :2341", "details": "uri=/api/v1/customer/2341" }
	 * 
	 */
	
	

	@GetMapping("/customer/{accountID}")
	public ResponseEntity<AccountDetails> getAccountDetailsByAccountId(@PathVariable("accountID") String accountID) {

		LOGGER.info("request received for accountId{} " + accountID);
		CustomerTable customer = customerRepository.findCustomerByAccountID(accountID);
		if (customer == null) {
			LOGGER.info("No customer exist with Id {}" + accountID);
			throw new CustomerNotFoundException("No customer exist with Id :" + accountID);
		}
		Double accountBalance = accountRepository.getAccountBalance(accountID);
		AccountDetails accDetails = new AccountDetails();
		accDetails.setAccountID(customer.getAccountId());
		accDetails.setCustomerName(customer.getCustomerName());
		accDetails.setAccountBalance(accountBalance);
		LOGGER.info("account details sent in the response {} " + accDetails);
		return ResponseEntity.ok().body(accDetails);

	}


	/*
	 * @api{post}
	 * 
	 * @apiVersion 1.0.0
	 * 
	 * @apiDescription create new user
	 * 
	 * @apiDescription
	 * 
	 * @apiUri http://localhost:8080/api/v1/customer
	 * @apiParam
	 * 		{  "accountID":"2345",
				"customerName":"Pathak",
				"initialCredit":101
			}
	 *  
	 *    onSuccess 
	 *    
	 *    location →/customer/2345
	 *    
	 
	 * customer information already  exist
			{
			    "timestamp": "2020-10-12T05:52:34.292+00:00",
			    "message": "customer with account id 2345 already exist",
			    "details": "uri=/api/v1/customer"
			}	
	 */

	@PostMapping("/customer")
	public ResponseEntity<String> createAccount(@RequestBody Customer customer) {
		LOGGER.info("request received to create Customer{} " + customer);
		CustomerTable customerData = customerRepository.findCustomerByAccountID(customer.getAccountID());
		if (customerData != null) {
			LOGGER.info("customer with account id {} already exist" + customer.getAccountID());
			throw new CustomerAlreadyExistException(
					"customer with account id " + customer.getAccountID() + " already exist");
		}

		customerRepository.save(customer);
		LOGGER.info("customer{} saved " + customer);
		if (customer.getInitialCredit() > 0) {
			accountRepository.addAccountBalance(customer.getAccountID(), customer.getInitialCredit());
		}
		return ResponseEntity.created(URI.create("/customer/" + customer.getAccountID())).build();

	}
	

	/*
	 * @api{put}
	 * 
	 * @apiVersion 1.0.0
	 * 
	 * @apiDescription update account balance
	 * 
	 * @apiDescription
	 * 
	 * @apiUri http://localhost:8080/api/v1/	customer
	 * @apiParam
	 * 		{  "accountID":"2345",
				"balanceChange":507
				
			}
	 *  
	 *    onSuccess {
	 *    
	 *   return the updated balance
	 *    }
	 
	 	 * when customer balance is negative customer 
			{
			    "timestamp": "2020-10-12T05:55:44.815+00:00",
			    "message": "insufficient balance to process this request",
			    "details": "uri=/api/v1/customer"
			}	
	 */



	@PutMapping("/customer")
	public double UpdateAccountBalance(@RequestBody BalanceTransaction balanceTransaction) {
		LOGGER.info(
				"received request to update the acoount balance of account id {} " + balanceTransaction.getAccountID());
		CustomerTable customerData = customerRepository.findCustomerByAccountID(balanceTransaction.getAccountID());
		
		if (customerData == null) {
			LOGGER.info("customer with account id {} does not exist" + balanceTransaction.getAccountID());
			throw new CustomerAlreadyExistException(
					"No customer exist with accountId :" + balanceTransaction.getAccountID());
		}
		double updateAccountBalance = accountRepository.updateAccountBalance(balanceTransaction);
		return updateAccountBalance;

	}
	/*
	 * @api{get}
	 * 
	 * @apiVersion 1.0.0
	 * 
	 * @apiDescription provide the history on balance change in sorted formet based on updated date  of already existing customer
	 * 
	 * @apiDescription
	 * 
	 * @apiUri http://localhost:8080/api/v1/history/customer/{accountID}
	 *  onSuccess 
			[
		    {
		        "accountId": "2345",
		        "customerName": "Pathak",
		        "balanceChange": 2422,
		        "transactionTimestump": "2020-10-12T05:58:29.425+00:00"
		    },
		    {
		        "accountId": "2345",
		        "customerName": "Pathak",
		        "balanceChange": 1515,
		        "transactionTimestump": "2020-10-12T05:58:27.796+00:00"
		    },
		    {
		        "accountId": "2345",
		        "customerName": "Pathak",
		        "balanceChange": 608,
		        "transactionTimestump": "2020-10-12T05:54:45.525+00:00"
		    },
		    {
		        "accountId": "2345",
		        "customerName": "Pathak",
		        "balanceChange": 101,
		        "transactionTimestump": "2020-10-12T05:47:09.744+00:00"
		    }
		]
 *  when customer information does not exist
		 *  {
		    "timestamp": "2020-10-12T06:00:10.327+00:00",
		    "message": "No customer exist with Id :2347",
		    "details": "uri=/api/v1/customer/history/2347"
			}
	 */
	
	
	
	@GetMapping("/customer/history/{accountID}")
	public List<TransactionTable> getAccountHistoryByAccountId(@PathVariable("accountID") String accountID) {
		LOGGER.info("received request to get history for account id {} " + accountID);
		CustomerTable customer = customerRepository.findCustomerByAccountID(accountID);
		if (customer == null) {
			LOGGER.info("customer with account id {} does not exist" + accountID);
			throw new CustomerNotFoundException("No customer exist with Id :" + accountID);
		}
		return historyRepository.getTranshistorybyAccountId(accountID);

	}

}
