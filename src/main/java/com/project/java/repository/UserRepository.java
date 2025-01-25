package com.project.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java.model.User;



public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
	
	Optional<User> findByGoogleAccountId(String googleAccountId);
	
	boolean existsByGoogleAccountId(String googleAccountId);
	
	
}
