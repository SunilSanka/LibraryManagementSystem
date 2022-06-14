package com.librarymanagement.libraryusersaccount;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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

import com.librarymanagement.libraryusersaccount.exception.LibraryUserAccountNotFound;


@RestController
public class LibraryUserAccountController {
	
	@Autowired
	private LibraryUserAccountRepo libraryUserAccountRepo;
	
	@GetMapping("/lbm/libraryuseraccounts")
	public List<LibraryUserAccount> getAllLibraryUserAccounts(){
		return libraryUserAccountRepo.findAll();
	}
	
	@GetMapping("/lbm/libraryuseraccounts/{userid}")
	public LibraryUserAccount getLibraryUserAccount(@PathVariable int userid) {
		Optional<LibraryUserAccount> userAccountOptional = libraryUserAccountRepo.findByUserid(userid);
		 if(!userAccountOptional.isPresent()) {
			 throw new LibraryUserAccountNotFound("userid :"+userid);
		 }
		return userAccountOptional.get();
	}
	
	@PostMapping("/lbm/libraryuseraccounts")
	public ResponseEntity<LibraryUserAccount> addUserAccount(@RequestBody LibraryUserAccount libraryUserAccount) {
		LibraryUserAccount savedLibraryUserAccount = libraryUserAccountRepo.save(libraryUserAccount);
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedLibraryUserAccount.getId())
					.toUri();
		return ResponseEntity.created(location).build();
	}
	
	// Update User AccountRequested
	@PutMapping("/lbm/libraryuseraccounts")
	public ResponseEntity<LibraryUserAccount> updateuserAccount(@RequestBody LibraryUserAccount libraryUserAccount) {
		
		Optional<LibraryUserAccount> userAccountOptional = libraryUserAccountRepo.findById(libraryUserAccount.getId());
		 if(!userAccountOptional.isPresent()) {
			 throw new LibraryUserAccountNotFound("userid :"+libraryUserAccount.getId());
		 }
		LibraryUserAccount savedLibraryUserAccount = libraryUserAccountRepo.save(libraryUserAccount);
	    
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedLibraryUserAccount.getId())
					.toUri();
		return ResponseEntity.created(location).build();
	}
	
	//DELETE User Account
	@DeleteMapping("/lbm/libraryuseraccounts/{id}")
	public void updateuserAccount(@PathVariable int id) {
		
		libraryUserAccountRepo.deleteById(id);
	}
	

}
