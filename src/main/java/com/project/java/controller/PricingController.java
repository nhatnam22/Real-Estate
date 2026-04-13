package com.project.java.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.model.Pricing;
import com.project.java.responses.ClassResponse;
import com.project.java.service.PricingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @GetMapping("/get-all")
    public ResponseEntity<ClassResponse> getAllPricings() {
        List<Pricing> pricings = pricingService.getAllPricings();
        return ResponseEntity.ok(ClassResponse.builder()
                .status(HttpStatus.OK)
                .message("Lấy danh sách gói thành công (Get all pricings successfully)")
                .data(pricings)
                .build());
    }
}
