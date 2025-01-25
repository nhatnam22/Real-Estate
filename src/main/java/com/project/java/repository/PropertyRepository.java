package com.project.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.project.java.model.Property;
import com.project.java.responses.property.PropertyResponse;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property>{

}
