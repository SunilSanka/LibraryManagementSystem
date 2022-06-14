package com.librarymanagement.libraryusers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepo extends JpaRepository<LibraryUser, Integer> {

}
