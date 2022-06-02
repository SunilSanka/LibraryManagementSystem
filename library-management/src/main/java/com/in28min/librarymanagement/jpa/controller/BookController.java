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
import com.in28min.librarymanagement.jpa.exception.BookNotFoundException;
import com.in28min.librarymanagement.jpa.repository.BookRepository;


@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping("/lb/books")
	public List<Book> retrieveAllBooks(){
		return bookRepository.findAll();
	}
	
	@GetMapping("/lb/books/{id}")
	public Book retriveBook(@PathVariable int id) {
		Optional<Book> book = bookRepository.findById(id);
		if(book.isEmpty()) {
			throw new BookNotFoundException("id - "+id);
		}
		return book.get();
	}
	
	@PostMapping("/lb/books/")
	public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
		Book savedBook = bookRepository.save(book);
		URI	location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();
		return ResponseEntity.created(location).build();
				
	}
	
	@DeleteMapping("/lb/books/")
	public void deleteBook(@PathVariable int id) {
		bookRepository.deleteById(id);
	}

}
