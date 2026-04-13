package com.project.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.project.java.model.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property>{

    // This query sorts properties such that those with active boosts appear first,
    // followed by others.
    @Query("SELECT p FROM Property p " +
           "ORDER BY " +
           "CASE WHEN p.expiredBoost > CURRENT_TIMESTAMP THEN 1 ELSE 2 END ASC, " +
           "p.id DESC")
    List<Property> findAllWithBoostsPrioritized();
}
