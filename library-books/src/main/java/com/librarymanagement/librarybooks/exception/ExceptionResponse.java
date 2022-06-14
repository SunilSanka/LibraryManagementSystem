package com.librarymanagement.librarybooks.exception;

import java.util.Date;

public class ExceptionResponse {
	
	private Date timetamp;
	private String message;
	private String details;
	
	
	public ExceptionResponse(Date timetamp, String message, String details) {
		super();
		this.timetamp = timetamp;
		this.message = message;
		this.details = details;
	}
	public ExceptionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getTimetamp() {
		return timetamp;
	}
	public void setTimetamp(Date timetamp) {
		this.timetamp = timetamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	

}
