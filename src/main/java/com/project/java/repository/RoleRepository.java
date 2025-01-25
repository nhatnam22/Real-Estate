package com.project.java.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{
	boolean existsById (Long roleID);
	
	Optional<Role> findByName (String roleName);
	
}
