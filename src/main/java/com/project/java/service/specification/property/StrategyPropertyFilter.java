package com.project.java.service.specification.property;

import org.springframework.data.jpa.domain.Specification;
import com.project.java.dto.PropertyFilterDTO;
import com.project.java.model.Property;

public class StrategyPropertyFilter {

    public static class ProvinceFilter implements IPropertySpecification {
        @Override
        public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO) {
            return (root, query, criteriaBuilder) -> {
                if (propertyFilterDTO.getProvince() != null) {
                    return criteriaBuilder.equal(root.get("province"), propertyFilterDTO.getProvince());
                }
                return null;
            };
        }
    }

    public static class DistrictFilter implements IPropertySpecification {
        @Override
        public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO) {
            return (root, query, criteriaBuilder) -> {
                if (propertyFilterDTO.getDistrict() != null) {
                    return criteriaBuilder.equal(root.get("district"), propertyFilterDTO.getDistrict());
                }
                return null;
            };
        }
    }

    public static class WardFilter implements IPropertySpecification {
        @Override
        public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO) {
            return (root, query, criteriaBuilder) -> {
                if (propertyFilterDTO.getWard() != null) {
                    return criteriaBuilder.equal(root.get("ward"), propertyFilterDTO.getWard());
                }
                return null;
            };
        }
    }

    public static class PriceFilter implements IPropertySpecification {
        @Override
        public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO) {
            return (root, query, criteriaBuilder) -> {
                if (propertyFilterDTO.getPrice() != null) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("price"), propertyFilterDTO.getPrice());
                }
                return null;
            };
        }
    }

    public static class ListingTypeFilter implements IPropertySpecification {
        @Override
        public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO) {
            return (root, query, criteriaBuilder) -> {
                if (propertyFilterDTO.getListingType() != null) {
                    return criteriaBuilder.equal(root.get("listingType"), propertyFilterDTO.getListingType());
                }
                return null;
            };
        }
    }

    public static class PropertyTypeFilter implements IPropertySpecification {
        @Override
        public Specification<Property> getSpecification(PropertyFilterDTO propertyFilterDTO) {
            return (root, query, criteriaBuilder) -> {
                if (propertyFilterDTO.getPropertyType() != null) {
                    return criteriaBuilder.equal(root.get("propertyType"), propertyFilterDTO.getPropertyType());
                }
                return null;
            };
        }
    }
}
