package com.project.java.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.java.dto.BoostRequestDTO;
import com.project.java.dto.PropertyDTO;
import com.project.java.exception.CheckDataException;
import com.project.java.model.Pricing;
import com.project.java.model.Property;
import com.project.java.model.PropertyStatus;
import com.project.java.model.ReviewStatus;
import com.project.java.model.User;
import com.project.java.repository.PricingRepository;
import com.project.java.repository.PropertyRepository;
import com.project.java.repository.UserRepository;

@Service
public class IPropertyService implements PropertyService {
	
	private final LocationService locationService;
	private final PropertyRepository propertyRepository;
	private final UserRepository userRepository;
	private final PricingRepository pricingRepository;

	@Override
	public Property createProperty(PropertyDTO propertyDTO) throws Exception {
		if (propertyDTO.getSize() <= 0) {
			throw new IllegalArgumentException("Diện tích phải lớn hơn 0");
		}
		if (propertyDTO.getPrice() <= 0 && propertyDTO.getPrice() != null) {
			throw new IllegalArgumentException("Giá phải lớn hơn 0");
		}
		User postedBy = userRepository.findByEmail(propertyDTO.getEmail()).orElseThrow(()->
		new CheckDataException("Không tìm thấy thông tin người đăng"));
		
		String districtExtract = locationService.extractDistrict(propertyDTO.getAddress());
		String provinceExtract = locationService.extractProvince(propertyDTO.getAddress());
		String wardExtract = locationService.extractWard(propertyDTO.getAddress());
		
		Property newProperty = new Property.Builder()
				.name(propertyDTO.getName())
				.price(propertyDTO.getPrice())
				.address(propertyDTO.getAddress())
				.description(propertyDTO.getDescription())
				.bathroom(propertyDTO.getBathroom())
				.bedroom(propertyDTO.getBedroom())
				.size(propertyDTO.getSize())
				.direction(propertyDTO.getDirection())
				.images(propertyDTO.getImages().stream().findFirst().orElse(null))
				.yearBuilt(propertyDTO.getYearBuilt())
				.listingType(propertyDTO.getListingType())
				.district(districtExtract)
				.province(provinceExtract)
				.ward(wardExtract)
				.avgStar(0)
				.postedBy(postedBy)
				.ratings(null)
				.verified(false)
				.features(null)
				.status(PropertyStatus.AVAILABLE)
				.statusReview(ReviewStatus.PENDING)
				.build();
		 propertyRepository.save(newProperty);
		 return null;
	}

	@Override
	public boolean verifyPost(PropertyDTO propertyDTO) throws Exception {
		
		return false;
	}

	@Override
	public Property updateProperty(PropertyDTO propertyDTO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProperty(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	public IPropertyService(LocationService locationService, PropertyRepository propertyRepository, UserRepository userRepository, PricingRepository pricingRepository) {
		super();
		this.locationService = locationService;
		this.propertyRepository = propertyRepository;
		this.userRepository= userRepository;
		this.pricingRepository = pricingRepository;
	}

	@Override
	public List<Property> getAllProperties() throws Exception {
		// return prioritized list
		return propertyRepository.findAllWithBoostsPrioritized();
	}

	@Override
	@Transactional
	public Property boostProperty(BoostRequestDTO boostRequest, String userEmail) throws Exception {
	    // 1. Get the pricing package
	    Pricing pricing = pricingRepository.findById(boostRequest.getPricingId())
	        .orElseThrow(() -> new CheckDataException("Pricing package not found"));
	        
	    Float requiredAmount = Float.valueOf(pricing.getPrice());
	    
	    // Parse days from expireDate (assuming expireDate string contains days, e.g. "30" or "7")
	    long daysToBoost;
	    try {
	        daysToBoost = Long.parseLong(pricing.getExpireDate());
	    } catch (NumberFormatException e) {
	        daysToBoost = 30; // default 30 days if format is invalid
	    }

	    // 2. Get the user and verify balance
	    User user = userRepository.findByEmail(userEmail)
	        .orElseThrow(() -> new CheckDataException("User not found"));
	        
	    if (user.getMoney() == null || user.getMoney() < requiredAmount) {
	        throw new Exception("Insufficient balance to boost property. Please top up your wallet.");
	    }
	    
	    // 3. Get the property and verify ownership
	    Property property = propertyRepository.findById(boostRequest.getPropertyId())
	        .orElseThrow(() -> new CheckDataException("Property not found"));
	        
	    if (!property.getPostedBy().getId().equals(user.getId())) {
	        throw new Exception("You are not authorized to boost this property");
	    }
	    
	    // 4. Deduct balance from user
	    user.setMoney(user.getMoney() - requiredAmount);
	    userRepository.save(user);
	    
	    // 5. Update the property's boost expiration date
	    LocalDateTime currentExpiration = property.getExpiredBoost();
	    LocalDateTime now = LocalDateTime.now();
	    
	    // If it's already boosted and not expired, extend the duration
	    if (currentExpiration != null && currentExpiration.isAfter(now)) {
	        property.setExpiredBoost(currentExpiration.plusDays(daysToBoost));
	    } else {
	        // Otherwise, set a new expiration from now
	        property.setExpiredBoost(now.plusDays(daysToBoost));
	    }
	    
	    return propertyRepository.save(property);
	}


}
