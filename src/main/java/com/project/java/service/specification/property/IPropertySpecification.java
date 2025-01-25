package com.project.java.service.specification.property;

import org.springframework.data.jpa.domain.Specification;

import com.project.java.dto.PropertyFilterDTO;
import com.project.java.model.Property;

public interface IPropertySpecification {
	public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO);
}
