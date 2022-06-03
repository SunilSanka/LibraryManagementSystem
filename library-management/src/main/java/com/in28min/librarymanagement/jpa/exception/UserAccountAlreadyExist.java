package com.in28min.librarymanagement.jpa.exception;

public class UserAccountAlreadyExist extends RuntimeException {
	public UserAccountAlreadyExist(String message) {
		super(message);
	}
}
