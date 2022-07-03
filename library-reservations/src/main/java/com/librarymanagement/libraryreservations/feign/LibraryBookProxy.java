package com.librarymanagement.libraryreservations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.librarymanagement.libraryreservations.LibraryBook;
//If we want to talk to different instance running on a different port i.e. 8201, then we need
//to change 'url'. To have proper load balancer. 

// If we are using Eureka and Feign, load balancer will come for free.
// Ribbon was used earlier as a loadbalancer

//@FeignClient(name="library-books", url="localhost:8200") => Remove url

//CHANGE-KUBERNETES
//@FeignClient(name="library-books")
@FeignClient(name="library-books", url="${LIBRARY_BOOKS_SERVICE_HOST:http://localhost}:8200")
//@FeignClient(name="library-books", url="${LIBRARY_BOOKS_URI:http://localhost}:8200")
public interface LibraryBookProxy {
	
	@GetMapping("/lbm/librarybooks/{bookid}")
	public LibraryBook retriveBook(@PathVariable int bookid);
	
	@PutMapping("/lbm/librarybooks")
	public ResponseEntity<LibraryBook> updateBook(@RequestBody LibraryBook libraryBook);
	
}
