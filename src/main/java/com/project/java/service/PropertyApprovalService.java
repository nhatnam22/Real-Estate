package com.project.java.service;

import org.springframework.stereotype.Service;

import com.project.java.exception.CheckDataException;
import com.project.java.model.Property;
import com.project.java.model.ReviewStatus;
import com.project.java.model.User;
import com.project.java.repository.PropertyRepository;
import com.project.java.repository.UserRepository;

@Service
public class PropertyApprovalService {
	private final PropertyRepository propertyRepository;
	private final UserRepository userRepository;
	
	public Property approveProperty(Long id) throws Exception {
		Property property = propertyRepository.findById(id).get();
		
		property.setVerified(true);
		property.setStatusReview(ReviewStatus.APPROVED);
		
		Property newProperty = propertyRepository.save(property);
		
		return newProperty;
	}
	
	public Property rejectProperty(Long id) throws Exception {
		Property property = propertyRepository.findById(id).get();
		
		property.setStatusReview(ReviewStatus.REJECTED);
		
		return propertyRepository.save(property);
	}
	
	public Boolean isEligibleForAutoVerification(Property property) throws Exception {
		User userVerification = property.getPostedBy();
		if(userVerification == null) {
			throw new CheckDataException("User is exist");
		}
		if(userVerification.getCitizenIdentityCard().isEmpty() || userVerification.getCitizenIdentityCard().size() < 2) {
			property.setStatusReview(ReviewStatus.PENDING);
			propertyRepository.save(property);
			return false;
		}
		property.setStatusReview(ReviewStatus.APPROVED);
		property.setVerified(true);
		propertyRepository.save(property);
		return true;
		
	}

	public PropertyApprovalService(PropertyRepository propertyRepository, UserRepository userRepository) {
		super();
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;
	}
	


}
