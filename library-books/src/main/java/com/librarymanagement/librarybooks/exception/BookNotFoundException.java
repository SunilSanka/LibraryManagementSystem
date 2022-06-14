package com.librarymanagement.librarybooks.exception;

public class BookNotFoundException extends RuntimeException{
	public BookNotFoundException(String message) {
		super(message);
	}
}
