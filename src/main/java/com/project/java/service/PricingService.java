package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.java.model.Pricing;

@Service
public interface PricingService {
    List<Pricing> getAllPricings();
}
