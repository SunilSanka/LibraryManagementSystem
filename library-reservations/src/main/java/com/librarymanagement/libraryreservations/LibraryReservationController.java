
package com.librarymanagement.libraryreservations;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
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

import com.librarymanagement.libraryreservations.exceptions.LibraryResvNotFound;

@RestController
public class LibraryReservationController {
	
	@Autowired
	private LibraryReservationRepository resvRepo;
	
	@Autowired
	private RestTemplateClient restTemplateClient;
	
	private LibraryBook libraryBook; 
	private LibraryUserAccount libraryUserAccount;
	private final int FINE=1500;
	
	@GetMapping("/lbm/libraryreservations")
	public List<LibraryReservations>  getAllReservation() {
		return resvRepo.findAll();
	}
	
	@GetMapping("/lbm/libraryreservations/{resvid}")
	public LibraryReservations  getReservation(@PathVariable int resvid) {
		Optional<LibraryReservations> resvOptional = resvRepo.findById(resvid);
		return resvOptional.get();
	}
	
	@PostMapping("/lbm/libraryreservations")
	public ResponseEntity<LibraryReservations> createReservation(@RequestBody LibraryReservations reservation){
		String resStatus="Waiting";
		
		HashMap<String, Integer> uriVariables = new HashMap<String, Integer>();
		uriVariables.put("userid", reservation.getUserId());
		uriVariables.put("bookid", reservation.getBookid());
		
		ResponseEntity<LibraryUser> userOptional = restTemplateClient.restTemplate().getForEntity("http://localhost:8100/lbm/users/{userid}", LibraryUser.class, uriVariables);
		if(userOptional.getBody() == null) {
			return null;
		}
		
		libraryBook = restTemplateClient.restTemplate().getForEntity("http://localhost:8200/lbm/librarybooks/{bookid}/", LibraryBook.class, uriVariables).getBody(); 
		libraryUserAccount = restTemplateClient.restTemplate().getForEntity("http://localhost:8300/lbm/libraryuseraccounts/{userid}/", LibraryUserAccount.class, uriVariables).getBody(); 

		//Book Availability
		if(libraryBook.getCopiesAvailable() >= 1) { 
			resStatus = "Reserved";
			 
			//Library UserAccount - Reserved Books
			libraryUserAccount.setReservedbooks(libraryUserAccount.getReservedbooks()+1);
			
			//Library UserAccount - Requested Books
			libraryUserAccount.setRequestedbooks(libraryUserAccount.getRequestedbooks()+1);
			
			//Library Book - Available Copies
			libraryBook.setCopiesAvailable(libraryBook.getCopiesAvailable()-1);
			
			restTemplateClient.restTemplate().put("http://localhost:8200/lbm/librarybooks/",libraryBook);
			restTemplateClient.restTemplate().put("http://localhost:8300/lbm/libraryuseraccounts/",libraryUserAccount);
		}
		
		reservation.setEntryDate(new Date());
		reservation.setStatus(resStatus);
		
		 LibraryReservations savedLibraryReservation = resvRepo.save(reservation);
		
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedLibraryReservation.getId())
			.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/lbm/libraryreservations/{id}/status/{status}")
	public ResponseEntity<LibraryReservations> updateResStatus(@PathVariable int id, @PathVariable String status){
		
		HashMap<String, Integer> uriVariables = new HashMap<String, Integer>();
		Optional<LibraryReservations> libraryResvOptional = resvRepo.findById(id);
		
		if(!libraryResvOptional.isPresent()) {
			throw new LibraryResvNotFound("Reservation Id :"+id); 
		}
			
		LibraryReservations savedLibResv = libraryResvOptional.get();
		
		if(savedLibResv.getStatus().equalsIgnoreCase("Return") || savedLibResv.getStatus().equalsIgnoreCase("Lost")) {
			throw new LibraryResvNotFound("Reservation Id :"+id);
		}
		
		uriVariables.put("userid", savedLibResv.getUserId());
		uriVariables.put("bookid", savedLibResv.getBookid());
	
		libraryBook = restTemplateClient.restTemplate().getForEntity("http://localhost:8200/lbm/librarybooks/{bookid}/", LibraryBook.class, uriVariables).getBody();
		
		libraryUserAccount = restTemplateClient.restTemplate().getForEntity("http://localhost:8300/lbm/libraryuseraccounts/{userid}/", LibraryUserAccount.class, uriVariables).getBody(); 
		
	 switch(status) {
		
			case "Return": libraryBook.setCopiesAvailable(libraryBook.getCopiesAvailable()+1); 
						   libraryUserAccount.setReturnedbooks(libraryUserAccount.getReturnedbooks()+1);
						   restTemplateClient.restTemplate().put("http://localhost:8200/lbm/librarybooks/",libraryBook);
						   restTemplateClient.restTemplate().put("http://localhost:8300/lbm/libraryuseraccounts/",libraryUserAccount);
						   savedLibResv.setLeaseTime(0);
						   break;
						   
		    case "Extended": savedLibResv.setLeaseTime(savedLibResv.getLeaseTime()+15); 
							 break;
							 
			case "Lost": savedLibResv.setLeaseTime(0);
						 libraryBook.setCopiesAvailable(libraryBook.getCopiesAvailable()-1);
						 libraryUserAccount.setLostbooks(libraryUserAccount.getLostbooks()+1);
						 libraryUserAccount.setFineAmount(libraryUserAccount.getFineAmount()+FINE);
						 restTemplateClient.restTemplate().put("http://localhost:8200/lbm/librarybooks/",libraryBook);
						 restTemplateClient.restTemplate().put("http://localhost:8300/lbm/libraryuseraccounts/",libraryUserAccount);
						 break;
						 
			default: throw new LibraryResvNotFound("Reservation id"+id);
		}
	 //Save the Reservation Status
	 savedLibResv.setStatus(status);
		
	 savedLibResv = resvRepo.save(savedLibResv); 
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedLibResv.getId())
			.toUri();
		
		return ResponseEntity.created(uri).build();
	} 
	
	//Delete Reservation
	@DeleteMapping("/lbm/libraryreservations/{resvid}")
	public void deleteReservation(@PathVariable int resvid) {
		resvRepo.deleteById(resvid);
	}
}

