package com.librarymanagement.librarybooks;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRepo  extends JpaRepository<LibraryBook, Integer>{

	

}
