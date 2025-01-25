package com.project.java.service;

import org.springframework.stereotype.Service;

import com.project.java.dto.PropertyDTO;
import com.project.java.exception.CheckDataException;
import com.project.java.model.Property;
import com.project.java.model.PropertyStatus;
import com.project.java.model.ReviewStatus;
import com.project.java.model.User;
import com.project.java.repository.PropertyRepository;
import com.project.java.repository.UserRepository;

@Service
public class IPropertyService implements PropertyService {
	
	private final LocationService locationService;
	private final PropertyRepository propertyRepository;
	private final UserRepository userRepository;

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

	public IPropertyService(LocationService locationService, PropertyRepository propertyRepository, UserRepository userRepository) {
		super();
		this.locationService = locationService;
		this.propertyRepository = propertyRepository;
		this.userRepository= userRepository;
	}



}
