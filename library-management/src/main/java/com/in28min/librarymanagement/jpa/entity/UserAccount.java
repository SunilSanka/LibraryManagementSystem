package com.in28min.librarymanagement.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserAccount {
	
	@GeneratedValue
	@Id
	@JsonIgnore
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	private int borrowedbooks;
	private int reservedbooks;
	private int returnedbooks;
	private int lostbooks;
	private int fineAmount;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getBorrowedbooks() {
		return borrowedbooks;
	}
	public void setBorrowedbooks(int borrowedbooks) {
		this.borrowedbooks = borrowedbooks;
	}
	public int getReservedbooks() {
		return reservedbooks;
	}
	public void setReservedbooks(int reservedbooks) {
		this.reservedbooks = reservedbooks;
	}
	public int getReturnedbooks() {
		return returnedbooks;
	}
	public void setReturnedbooks(int returnedbooks) {
		this.returnedbooks = returnedbooks;
	}
	public int getLostbooks() {
		return lostbooks;
	}
	public void setLostbooks(int lostbooks) {
		this.lostbooks = lostbooks;
	}
	public int getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(int fineAmount) {
		this.fineAmount = fineAmount;
	}
	
	
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", borrowedbooks=" + borrowedbooks + ", reservedbooks="
				+ reservedbooks + ", returnedbooks=" + returnedbooks + ", lostbooks=" + lostbooks + ", fineAmount="
				+ fineAmount + "]";
	}

}
