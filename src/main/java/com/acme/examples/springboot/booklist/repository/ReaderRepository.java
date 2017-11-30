package com.acme.examples.springboot.booklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.examples.springboot.booklist.domain.ReaderEntity;

public interface ReaderRepository extends JpaRepository<ReaderEntity, String>{

}
