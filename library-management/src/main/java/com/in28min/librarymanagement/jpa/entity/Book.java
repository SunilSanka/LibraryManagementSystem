package com.in28min.librarymanagement.jpa.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Book {

	@GeneratedValue
	@Id
	private int id;
	private String title;
	private String ISBN;
	private String publication;
	
	@OneToMany(mappedBy = "book")
	private List<Feedback> feedbacks;
	
	@OneToMany(mappedBy = "book")
	private List<Reservation> reservation;
	
	private int copiesAvailable;
	
	public List<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}

	public Book() {
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

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
}
