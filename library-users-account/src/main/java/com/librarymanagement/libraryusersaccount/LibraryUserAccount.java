package com.librarymanagement.libraryusersaccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LibraryUserAccount {
	
	@GeneratedValue
	@Id
	private int id;
	private int userid;
	private int requestedbooks;
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
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRequestedbooks() {
		return requestedbooks;
	}
	public void setRequestedbooks(int requestedbooks) {
		this.requestedbooks = requestedbooks;
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
		return "LibraryUserAccount [id=" + id + ", userid=" + userid + ", requestedbooks=" + requestedbooks
				+ ", reservedbooks=" + reservedbooks + ", returnedbooks=" + returnedbooks + ", lostbooks=" + lostbooks
				+ ", fineAmount=" + fineAmount + "]";
	}
	
	
	
}
