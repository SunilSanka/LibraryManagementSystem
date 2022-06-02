package com.in28min.librarymanagement.jpa.exception;

import org.springframework.stereotype.Component;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String message) {
		super(message);
	}
}
