package com.in28min.librarymanagement.jpa.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28min.librarymanagement.jpa.entity.Book;
import com.in28min.librarymanagement.jpa.entity.Reservation;
import com.in28min.librarymanagement.jpa.entity.User;
import com.in28min.librarymanagement.jpa.exception.BookNotFoundException;
import com.in28min.librarymanagement.jpa.exception.ResvNotFound;
import com.in28min.librarymanagement.jpa.exception.UserNotFoundException;
import com.in28min.librarymanagement.jpa.repository.BookRepository;
import com.in28min.librarymanagement.jpa.repository.ReservationRepo;
import com.in28min.librarymanagement.jpa.repository.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ReservationController {
	
	@Autowired
	private ReservationRepo resvRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping("/lb/reservations")
	public List<Reservation> retrieveAllReservations(){
		return resvRepo.findAll();
	}
	
	@GetMapping("/lb/user/{id}/reservation")
	public EntityModel<List<Reservation>> retrieveUserReservation(@PathVariable int userid) {
		Optional<User> userOptional = userRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("userid - :"+userid);
		}
		User user = userOptional.get();
		List<Reservation> userReservations = user.getReservations();
		EntityModel<List<Reservation>> model = EntityModel.of(userReservations);
		WebMvcLinkBuilder linkToReservations = 
				linkTo(methodOn(this.getClass()).retrieveAllReservations());
		
		model.add(linkToReservations.withRel("all-reservations"));
		return model;
	}
	
	@GetMapping("/lb/book/{id}/reservation")
	public EntityModel<List<Reservation>> retrieveBookReservation(@PathVariable int bookid) {
		Optional<Book> bookOptional = bookRepo.findById(bookid);
		if(!bookOptional.isPresent()) {
			throw new UserNotFoundException("book id - :"+bookid);
		}
		Book book = bookOptional.get();
		List<Reservation> bookReservations = book.getReservation();
		EntityModel<List<Reservation>> model = EntityModel.of(bookReservations);
		WebMvcLinkBuilder linkToReservations = 
				linkTo(methodOn(this.getClass()).retrieveAllReservations());
		
		model.add(linkToReservations.withRel("all-reservations"));
		return model;
	}
	
	
	@PostMapping("/lb/uses/{userid}/book/{bookid}/reservation")
	public ResponseEntity<Reservation> createReservation(@PathVariable int userid, @PathVariable int bookid, @RequestBody Reservation reservation){
		String resStatus="Waiting";
		Optional<User> userOptional = userRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user id -"+userid);
		}
		Optional<Book> bookOptional = bookRepo.findById(bookid);
		if(!bookOptional.isPresent()) {
			throw new BookNotFoundException("book id -"+bookid);
		}
		User user = userOptional.get();
		Book book = bookOptional.get();
		
		reservation.setUser(user);
		reservation.setBook(book);
		
		int bookCount = book.getCopiesAvailable();
		
		if(bookCount >= 1) {
			resStatus = "Reserved";
			book.setCopiesAvailable(bookCount-1);
		}
		reservation.setStatus(resStatus);
		resvRepo.save(reservation);
		
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(reservation.getId())
			.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/lb/uses/{userid}/book/{bookid}/reservation/{resevid}/status/{status}")
	public ResponseEntity<Reservation> updateResStatus(@PathVariable int userid, @PathVariable int bookid, @PathVariable int resvId, @PathVariable String status){
		
		Optional<User> userOptional = userRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user id -"+userid);
		}
		Optional<Book> bookOptional = bookRepo.findById(bookid);
		if(!bookOptional.isPresent()) {
			throw new BookNotFoundException("book id -"+bookid);
		}

		Optional<Reservation> resvOptional = resvRepo.findById(resvId);
		if(!resvOptional.isPresent()) {
			throw new ResvNotFound("resv id "+resvId);
		}
		Book book  = bookOptional.get();
		
				
		Reservation savedResv = resvOptional.get();
		savedResv.setStatus(status);
		
		switch(status) {
			case "Return": book.setCopiesAvailable(book.getCopiesAvailable()+1);
			case "Extended": savedResv.setLeaseTime(savedResv.getLeaseTime()+15);
		}
		
		//Update the current reservation
		resvRepo.save(savedResv);
		
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedResv.getId())
			.toUri();
		
		return ResponseEntity.created(uri).build();
	}
		
}
