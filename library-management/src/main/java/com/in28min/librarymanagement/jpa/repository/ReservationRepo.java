package com.in28min.librarymanagement.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.in28min.librarymanagement.jpa.entity.Reservation;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Integer> {

}
