package com.librarymanagement.libraryreservations;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class LibraryReservations {
	@GeneratedValue
	@Id	
	private int Id;
	private Date entryDate;	
	private String status; // Reserved, Waiting, Returned, Lost
	private int leaseTime; 
	private int userId;
	private int bookid;

 	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLeaseTime() {
		return leaseTime;
	}

	public void setLeaseTime(int leaseTime) {
		this.leaseTime = leaseTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	@Override
	public String toString() {
		return "Reservations [Id=" + Id + ", entryDate=" + entryDate + ", status=" + status + ", leaseTime=" + leaseTime
				+ ", userId=" + userId +"]";
	}
}