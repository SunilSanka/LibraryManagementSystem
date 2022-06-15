package com.librarymanagement.libraryreservations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.librarymanagement.libraryreservations.LibraryBook;

@FeignClient(name="library-books", url="localhost:8200")
public interface LibraryBookProxy {
	
	@GetMapping("/lbm/librarybooks/{bookid}")
	public LibraryBook retriveBook(@PathVariable int bookid);
	
	@PutMapping("/lbm/librarybooks")
	public ResponseEntity<LibraryBook> updateBook(@RequestBody LibraryBook libraryBook);
	
}
