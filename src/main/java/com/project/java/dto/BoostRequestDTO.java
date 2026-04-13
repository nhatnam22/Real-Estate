package com.project.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoostRequestDTO {
    private Long propertyId;
    private Long pricingId; // ID of the pricing package to purchase
}
