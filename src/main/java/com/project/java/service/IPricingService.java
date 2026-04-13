package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.java.model.Pricing;
import com.project.java.repository.PricingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IPricingService implements PricingService {

    private final PricingRepository pricingRepository;

    @Override
    public List<Pricing> getAllPricings() {
        return pricingRepository.findAll();
    }
}
