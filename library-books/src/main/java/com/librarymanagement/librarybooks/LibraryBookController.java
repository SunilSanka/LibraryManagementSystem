package com.librarymanagement.librarybooks;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.librarymanagement.librarybooks.exception.BookNotFoundException;


@RestController
public class LibraryBookController {
	
	@Autowired
	LibraryBookRepo libraryBookRepo;
	
	@GetMapping("/lbm/librarybooks")
	public List<LibraryBook> retrieveAllBooks(){
		return libraryBookRepo.findAll();
	}
	
	@GetMapping("/lbm/librarybooks/{bookid}")
	public LibraryBook retriveBook(@PathVariable int bookid) {
		Optional<LibraryBook> bookOptional = libraryBookRepo.findById(bookid);
		if(!bookOptional.isPresent()) {
			throw new BookNotFoundException("book id :"+bookid);
		}
		return bookOptional.get();
	}
	
	
	@PostMapping("/lbm/librarybooks")
	public ResponseEntity<LibraryBook> addBook(@Valid @RequestBody LibraryBook libraryBook){
		LibraryBook savedBook = libraryBookRepo.save(libraryBook);
		URI	location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/lbm/librarybooks")
	public ResponseEntity<LibraryBook> updateBook(@RequestBody LibraryBook libraryBook){
		
		Optional<LibraryBook> bookOptional = libraryBookRepo.findById(libraryBook.getId());
		if(!bookOptional.isPresent()) {
			throw new BookNotFoundException("book id :"+libraryBook.getId());
		}
		
		LibraryBook savedBook = libraryBookRepo.save(libraryBook);
		URI	location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBook.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@GetMapping("/lbm/librarybooks/{bookid}/available")
	public int getCount(@PathVariable int bookid){
		Optional<LibraryBook> bookOptional =  libraryBookRepo.findById(bookid);
		if(!bookOptional.isPresent()) {
			throw new BookNotFoundException("book id - "+bookid);
		}
		return bookOptional.get().getCopiesAvailable();
	}
	
	
	@DeleteMapping("/lbm/librarybooks/{bookid}")
	public void deleteBook(@PathVariable int bookid) {
		Optional<LibraryBook> bookOption = libraryBookRepo.findById(bookid);
		if(!bookOption.isPresent()) {
			throw new BookNotFoundException("book id :"+bookid);
		}
		LibraryBook book = bookOption.get();
		//Delete Book
		libraryBookRepo.deleteById(bookid);	}

}
