package com.cgi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class CustomerAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = -2548165038211592004L;
	
	public CustomerAlreadyExistException(String msg) {
		super(msg);
	}

}
