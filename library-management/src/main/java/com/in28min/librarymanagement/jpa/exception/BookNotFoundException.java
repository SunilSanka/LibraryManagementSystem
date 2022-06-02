package com.in28min.librarymanagement.jpa.exception;

public class BookNotFoundException extends RuntimeException{
	public BookNotFoundException(String message) {
		super(message);
	}
}
