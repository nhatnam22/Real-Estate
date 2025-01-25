package com.project.java.service.specification.property;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.java.dto.PropertyFilterDTO;
import com.project.java.model.Property;
import com.project.java.repository.PropertyRepository;

@Service
public class PropertyFilterService {
	private final PropertyRepository propertyRepository;

	public PropertyFilterService(PropertyRepository propertyRepository) {
		super();
		this.propertyRepository = propertyRepository;
	}
	public Page<Property> searchProperty(PropertyFilterDTO propertyFilterDTO, int page, int size, String sort){
        PropertySpecificationBuilder builder = new PropertySpecificationBuilder();

        builder.addStrategy(new StrategyPropertyFilter.ProvinceFilter(), propertyFilterDTO);
        builder.addStrategy(new StrategyPropertyFilter.DistrictFilter(), propertyFilterDTO);
        builder.addStrategy(new StrategyPropertyFilter.WardFilter(), propertyFilterDTO);
        builder.addStrategy(new StrategyPropertyFilter.PriceFilter(), propertyFilterDTO);
        builder.addStrategy(new StrategyPropertyFilter.ListingTypeFilter(), propertyFilterDTO);
        builder.addStrategy(new StrategyPropertyFilter.PropertyTypeFilter(), propertyFilterDTO);
        
        Specification<Property> specification = builder.build();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return propertyRepository.findAll(specification, pageRequest);
	}
}
