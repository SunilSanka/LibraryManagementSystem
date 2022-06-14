package com.librarymanagement.libraryreservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryReservationRepository  extends JpaRepository<LibraryReservations, Integer>{

}
