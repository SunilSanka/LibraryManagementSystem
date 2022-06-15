package com.librarymanagement.libraryreservations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.librarymanagement.libraryreservations.LibraryUser;


@FeignClient(name = "library-users", url="localhost:8100")
public interface LibraryUserProxy {
	
	@GetMapping("/lbm/users/{userid}")
	public LibraryUser retrieveLibraryuser(@PathVariable int userid);
	
}
