package com.in28min.librarymanagement.jpa.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28min.librarymanagement.jpa.entity.Book;
import com.in28min.librarymanagement.jpa.entity.Feedback;
import com.in28min.librarymanagement.jpa.entity.User;
import com.in28min.librarymanagement.jpa.entity.UserAccount;
import com.in28min.librarymanagement.jpa.exception.BookNotFoundException;
import com.in28min.librarymanagement.jpa.exception.UserNotFoundException;
import com.in28min.librarymanagement.jpa.repository.BookRepository;
import com.in28min.librarymanagement.jpa.repository.FeedbackRepository;
import com.in28min.librarymanagement.jpa.repository.UserAccountRepository;
import com.in28min.librarymanagement.jpa.repository.UserRepository;



@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	FeedbackRepository feedbackRepo;
	
	@Autowired
	UserAccountRepository userAccountRepo;

	@GetMapping("/lb/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/lb/users/{id}")
	public EntityModel<User> retrieveOne(@PathVariable int id){
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isEmpty()) {
			throw new UserNotFoundException("id - "+id);
		}
		User user = userOptional.get();
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkToUsers = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());

		model.add(linkToUsers.withRel("all-users"));
		
		return model;
	}
	
	
	@GetMapping("/lb/users/{id}/feedback")
	public List<Feedback> retrieveAllUserFeedbacks(@PathVariable int id){
		Optional<User> useroptional = userRepository.findById(id);
		if(!useroptional.isPresent()) {
			throw new UserNotFoundException("user id - "+id);
		}
		return useroptional.get().getFeedbacks();
	}
	
	@GetMapping("/lb/users/{id}/useraccount")
	public UserAccount retrieveUserAccount(@PathVariable int id) {
		
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user id - "+id);
		}
		
		return userOptional.get().getUserAccount();
	}
	
	@PostMapping("/lb/users/")
	 public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser  = userRepository.save(user);
		
		//Save UserAccount
		UserAccount userAccount = new UserAccount();
		userAccount.setUser(savedUser);
		userAccountRepo.save(userAccount);
		
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
				
		return ResponseEntity.created(location).build();
	}
	

	
	@PostMapping("/lb/uses/{userid}/book/{bookid}/feedback")
	public ResponseEntity<Feedback> createFeedback(@PathVariable int userid, @PathVariable int bookid, @RequestBody Feedback feedback){
    	Optional<User> userOptional = userRepository.findById(userid);
    		if(!userOptional.isPresent()) {
    			throw new UserNotFoundException("user id - "+userid);
    		}
    		
    		
    	Optional<Book> bookOptional = bookRepository.findById(bookid);
    		if(!bookOptional.isPresent()) {
    			throw new BookNotFoundException("book id -"+bookid);
    		}
    		
    		
    	User user = userOptional.get();
    	feedback.setUser(user);
    	
    	Book book = bookOptional.get();
    	feedback.setBook(book);
    	
    	feedbackRepo.save(feedback);
    	
    	    	
    	URI location = ServletUriComponentsBuilder
    	 .fromCurrentRequest()
    	 .path("/{id}")
    	 .buildAndExpand(feedback.getId())
    	 .toUri();
    	
    	return ResponseEntity.created(location).build();
    	
	}
	
	@DeleteMapping("/lb/users/")
	public void  deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	
	}

}
