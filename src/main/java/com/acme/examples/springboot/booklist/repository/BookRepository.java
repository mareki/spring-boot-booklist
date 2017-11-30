package com.acme.examples.springboot.booklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.examples.springboot.booklist.domain.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long>{

	List<BookEntity> findByReader(String reader);
	
	/**
	 * NamedQuery 'BookEntity.findWithAuthor' will be applied (automatically by spring data jpa) 
	 */
	List<BookEntity> findWithAuthor(String author);
}
