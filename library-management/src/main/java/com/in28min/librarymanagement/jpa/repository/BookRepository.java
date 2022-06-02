package com.in28min.librarymanagement.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28min.librarymanagement.jpa.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
