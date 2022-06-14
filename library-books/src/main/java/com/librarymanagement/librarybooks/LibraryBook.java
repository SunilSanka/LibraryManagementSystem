package com.librarymanagement.librarybooks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LibraryBook {

	@GeneratedValue
	@Id
	private int id;
	private String title;
	private String ISBN;
	private String publication;
	private int copiesAvailable;


	public LibraryBook() {
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		this.ISBN = iSBN;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}

	public int getCopiesAvailable() {
		return copiesAvailable;
	}
	
	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
}
