package com.in28min.librarymanagement.jpa.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.in28min.librarymanagement.jpa.entity.Reservation;
import com.in28min.librarymanagement.jpa.entity.User;
import com.in28min.librarymanagement.jpa.exception.BookNotFoundException;
import com.in28min.librarymanagement.jpa.exception.UserNotFoundException;
import com.in28min.librarymanagement.jpa.repository.BookRepository;
import com.in28min.librarymanagement.jpa.repository.FeedbackRepository;


@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	FeedbackRepository feedbackRepo;
	
	@GetMapping("/lb/book")
	public List<Book> retrieveAllBooks(){
		return bookRepo.findAll();
	}
	
	@GetMapping("/lb/book/{id}")
	public Book retriveBook(@PathVariable int id) {
		Optional<Book> book = bookRepo.findById(id);
		if(book.isEmpty()) {
			throw new BookNotFoundException("id - "+id);
		}
		return book.get();
	}
	
	@GetMapping("/lb/book/{id}/feedback")
	public List<Feedback> retrieveBookFeedbacks(@PathVariable int id){
		Optional<Book> bookOptional = bookRepo.findById(id);
		if(!bookOptional.isPresent()) {
			throw new UserNotFoundException("book id - "+id);
		}
		return bookOptional.get().getFeedbacks();
	}
	
	@GetMapping("/lb/book/{id}/reservation")
	public List<Reservation> retrieveBookReservations(@PathVariable int id){
		Optional<Book> bookOptional =  bookRepo.findById(id);
		if(!bookOptional.isPresent()) {
			throw new UserNotFoundException("book id - "+id);
		}
		return bookOptional.get().getReservation();
	}
	
	
	@PostMapping("/lb/book")
	public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
		Book savedBook = bookRepo.save(book);
		URI	location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	
	@DeleteMapping("/lb/book")
	public void deleteBook(@PathVariable int id) {
		Optional<Book> bookOption = bookRepo.findById(id);
		if(!bookOption.isPresent()) {
			throw new BookNotFoundException("book id :"+id);
		}
		Book book = bookOption.get();
		
		//Delete all attached feedbacks
		List<Feedback> bookFeedback = book.getFeedbacks();
		if(!bookFeedback.isEmpty()) {
			bookFeedback.stream().forEach(feedback -> {
				feedbackRepo.deleteById(feedback.getId());
			});
		}
		
		//Delete All Reservations
		List<Reservation> bookReservations = book.getReservation();
		if(!bookReservations.isEmpty()) {
			bookReservations.stream().forEach(reservation -> {
				bookRepo.deleteById(reservation.getId());
			});
		}
		
		//Delete Book
		bookRepo.deleteById(id);
	}

}
