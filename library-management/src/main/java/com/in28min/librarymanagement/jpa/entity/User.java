package com.in28min.librarymanagement.jpa.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * @author sunil
 *
 */
@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2)
	private String name;
	
	@Past
	private Date birthDate;
	
	@OneToMany(mappedBy = "user")
	private List<Feedback> feedbacks;
	
	@OneToOne(mappedBy = "user")
	private UserAccount userAccount;
	
	@OneToMany(mappedBy = "user") 
	private List<Reservation> reservations;
	
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public User() {
		super();
	}
	
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	

}
