package com.librarymanagement.libraryreservations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.librarymanagement.libraryreservations.LibraryUserAccount;
//If we want to talk to different instance running on a different port i.e. 8301, then we need
//to change 'url'. To have proper load balancer. 

//If we are using Eureka and Feign, load balancer will come for free.
//Ribbon was used earlier as a loadbalancer

//@FeignClient(name="library-books", url="localhost:8300") => Remove url 
@FeignClient(name = "library-users-accounts")
public interface LibraryUserAccountProxy {
		
	@GetMapping("/lbm/libraryuseraccounts/{userid}")
	public LibraryUserAccount getLibraryUserAccount(@PathVariable int userid);
	
	@PutMapping("/lbm/libraryuseraccounts")
	public ResponseEntity<LibraryUserAccount> updateuserAccount(@RequestBody LibraryUserAccount libraryUserAccount); 
}
