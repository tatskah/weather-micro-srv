package com.taito.weather.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException(Long id) {
		super("Data not found by id: "+ id);
	}

	public DataNotFoundException(String msg) {
		super(msg);
	}

}
