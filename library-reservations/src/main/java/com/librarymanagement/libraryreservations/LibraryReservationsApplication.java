package com.librarymanagement.libraryreservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LibraryReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryReservationsApplication.class, args);
	}

}
