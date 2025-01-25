package com.project.java.service.specification.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.project.java.dto.PropertyFilterDTO;
import com.project.java.model.Property;


public class PropertySpecificationBuilder {
	private final List<Specification<Property>> specifications;
	public PropertySpecificationBuilder() {
		this.specifications= new ArrayList<Specification<Property>>();
	}
	
	public void addStrategy(IPropertySpecification strategy, PropertyFilterDTO propertyFilterDTO) {
		Specification<Property> specification = strategy.getSpecification(propertyFilterDTO);
		if(specification != null) {
			specifications.add(specification);
		}
	}
    public Specification<Property> build() {
        if (specifications.isEmpty()) {
            return null;
        }

        Specification<Property> result = specifications.get(0); // Bắt đầu với tiêu chí đầu tiên
        for (int i = 1; i < specifications.size(); i++) {
            result = result.and(specifications.get(i)); // Kết hợp với các tiêu chí khác
        }
        return result;
    }
}
