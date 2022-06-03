package com.in28min.librarymanagement.jpa.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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




@RestController
public class ReservationController {
	
	@Autowired
	private ReservationRepo resvRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping("/lb/reservation")
	public List<Reservation>  getAllReservation() {
		return resvRepo.findAll();
	}
	
	private final int FINE_AMOUNT = 500;
	
	@PostMapping("/lb/reservation/user/{userid}/book/{bookid}")
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
		
		int requestedBooks = user.getUserAccount().getRequestedbooks();
		int reservedBooks = user.getUserAccount().getReservedbooks();
		int bookCount = book.getCopiesAvailable();
		
		if(bookCount >= 1) {
			resStatus = "Reserved";
			user.getUserAccount().setRequestedbooks(requestedBooks+1);
			user.getUserAccount().setReservedbooks(reservedBooks+1);
			book.setCopiesAvailable(bookCount-1);
			
		}
		reservation.setStatus(resStatus);
		
			
		resvRepo.save(reservation);
		bookRepo.save(book);
		
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(reservation.getId())
			.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/lb/user/{userid}/book/{bookid}/reservation/{resevid}/status/{status}")
	public ResponseEntity<Reservation> updateResStatus(@PathVariable int userid, @PathVariable int bookid, @PathVariable int resevid, @PathVariable String status){
		
		Optional<User> userOptional = userRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("user id -"+userid);
		}
		Optional<Book> bookOptional = bookRepo.findById(bookid);
		if(!bookOptional.isPresent()) {
			throw new BookNotFoundException("book id -"+bookid);
		}

		Optional<Reservation> resvOptional = resvRepo.findById(resevid);
		if(!resvOptional.isPresent()) {
			throw new ResvNotFound("resv id "+resevid);
		}
		
		User user = userOptional.get();
		Book book  = bookOptional.get();
		
		int returnedBooks = user.getUserAccount().getReturnedbooks();
		int lostBooks = user.getUserAccount().getLostbooks();
		int fineAmount = user.getUserAccount().getFineAmount();
		
		Reservation savedResv = resvOptional.get();
		savedResv.setStatus(status);
		
		switch(status) {
			case "Return": book.setCopiesAvailable(book.getCopiesAvailable()+1);
						   savedResv.setLeaseTime(0);
						   user.getUserAccount().setReturnedbooks(returnedBooks+1);
						   bookRepo.save(book);
						   break;
			case "Extended": savedResv.setLeaseTime(savedResv.getLeaseTime()+15);
							break;
			case "Lost": savedResv.setLeaseTime(0); 
						 book.setCopiesAvailable(book.getCopiesAvailable()-1);
						 user.getUserAccount().setLostbooks(lostBooks+1);
						 user.getUserAccount().setFineAmount(fineAmount+FINE_AMOUNT);
						 bookRepo.save(book);
						 break;
			default: throw new ResvNotFound("Reservation id"+resevid);
		}
		
		
		resvRepo.save(savedResv); 
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedResv.getId())
			.toUri();
		
		return ResponseEntity.created(uri).build();
	}
		
}
