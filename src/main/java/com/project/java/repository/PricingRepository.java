package com.project.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.java.model.Pricing;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {
}
