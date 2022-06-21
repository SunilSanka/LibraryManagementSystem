package com.librarymanagement.libraryusers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.librarymanagement.libraryusers.exception.UserNotFoundException;

@RestController
public class LibraryUserController {
	
	private Logger logger = LoggerFactory.getLogger(LibraryUserController.class);

	@Autowired
	private LibraryUserRepo libraryUserRepo;

	@GetMapping("/lbm/users")
	private List<LibraryUser> retrieveAllUsers(){
		logger.info("retrieve all users ");
		return  libraryUserRepo.findAll();
	}

	@GetMapping("/lbm/users/{userid}")
	private LibraryUser retrieveLibraryuser(@PathVariable int userid) {
		Optional<LibraryUser> userOptional = libraryUserRepo.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("userid :"+userid);
		}
		return userOptional.get();
	}

	@PostMapping("/lbm/users")
	private ResponseEntity<LibraryUser> saveUser(@RequestBody LibraryUser libraryUser){
		LibraryUser savedUser = libraryUserRepo.save(libraryUser);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{userid}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("lbm/users/{userid}")
	private void deleteUser(@PathVariable int userid) {
		libraryUserRepo.deleteById(userid);
	}

}