package com.company.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.company.project.model.Client;

public interface ClientRepository extends MongoRepository<Client, String>{

	public Page<Client> findAll(Pageable pageable);
}