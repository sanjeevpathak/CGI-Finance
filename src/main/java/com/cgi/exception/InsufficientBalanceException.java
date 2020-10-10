package com.cgi.exception;

public class InsufficientBalanceException extends RuntimeException{

	private static final long serialVersionUID = 5641829194477398845L;
	
	public InsufficientBalanceException(String msg) {
		super(msg);
	}

}
