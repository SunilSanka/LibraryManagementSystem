package com.in28min.librarymanagement.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.in28min.librarymanagement.jpa.repository.BookRepository;
import com.in28min.librarymanagement.jpa.repository.UserAccountRepository;
import com.in28min.librarymanagement.jpa.repository.UserRepository;

@RestController
public class UserAccountController {
		
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	UserAccountRepository userAccountRepo;
	/*
	@GetMapping("/lb/	")
	public List<UserAccount> retrieveAlluserAccounts() {
		return userAccountRepo.findAll();
	} */
	/*
	@GetMapping("/lb/useraccount/user/{userid}")
	public UserAccount retrieveUserAccount(@PathVariable int userid) {
		Optional<User> userOptional = userRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user id:"+userid);
		}
		User user = userOptional.get();
		return user.getUserAccount();
	} */
	
	//No need to expose an API call to create user account, an account is created for each newly created user
	/* 
	@PostMapping("/lb/user/{userid}/useraccount")
	public ResponseEntity<UserAccount> createUserAccount(@PathVariable int userid) {
		Optional<User> userOptional = userRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user id:"+userid);
		}
		User user = userOptional.get();
		if(user.getUserAccount() != null) {
			throw new UserAccountAlreadyExist("user account"+user.getUserAccount());
		}
		UserAccount userAccount = new UserAccount();
		user.setUserAccount(userAccount);
		userAccountRepo.save(userAccount);
		
		URI uri = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(userAccount.getId())
		.toUri();
		
		return ResponseEntity.created(uri).build();
		
	} */
	
	
}
