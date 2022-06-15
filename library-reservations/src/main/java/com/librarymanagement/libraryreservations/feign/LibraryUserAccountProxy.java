package com.librarymanagement.libraryreservations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.librarymanagement.libraryreservations.LibraryUserAccount;

@FeignClient(name = "library-users-accounts", url="localhost:8300")
public interface LibraryUserAccountProxy {
		
	@GetMapping("/lbm/libraryuseraccounts/{userid}")
	public LibraryUserAccount getLibraryUserAccount(@PathVariable int userid);
	
	@PutMapping("/lbm/libraryuseraccounts")
	public ResponseEntity<LibraryUserAccount> updateuserAccount(@RequestBody LibraryUserAccount libraryUserAccount); 
}
