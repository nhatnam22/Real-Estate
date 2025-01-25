package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.java.model.PropertyType;
import com.project.java.repository.PropertyTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IPropertyTypeService implements PropertyTypeService{
	
	private final PropertyTypeRepository propertyTypeRepository;

	@Override
	public List<PropertyType> getAllPropertyType() throws Exception {
		List<PropertyType> listType = propertyTypeRepository.findAll();
		
		return listType;
	}

	public IPropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
		super();
		this.propertyTypeRepository = propertyTypeRepository;
	}

}
