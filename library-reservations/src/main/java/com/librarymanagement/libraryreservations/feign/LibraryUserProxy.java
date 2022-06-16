package com.librarymanagement.libraryreservations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.librarymanagement.libraryreservations.LibraryUser;
//If we want to talk to different instance running on a different port i.e. 8101, then we need
//to change 'url'. To have proper load balancer. 

//If we are using Eureka and Feign, load balancer will come for free.
//Ribbon was used earlier as a loadbalancer

//@FeignClient(name="library-books", url="localhost:8100") => Remove url 
@FeignClient(name = "library-users")
public interface LibraryUserProxy {
	
	@GetMapping("/lbm/users/{userid}")
	public LibraryUser retrieveLibraryuser(@PathVariable int userid);
	
}
