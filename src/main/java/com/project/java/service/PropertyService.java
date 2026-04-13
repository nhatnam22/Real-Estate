package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.java.dto.BoostRequestDTO;
import com.project.java.dto.PropertyDTO;
import com.project.java.model.Property;

@Service
public interface PropertyService {

    Property createProperty(PropertyDTO propertyDTO) throws Exception;
    
    boolean verifyPost(PropertyDTO propertyDTO) throws Exception;
    
    Property updateProperty(PropertyDTO propertyDTO) throws Exception;
    
    List<Property> getAllProperties() throws Exception;
    
    void deleteProperty(Long id) throws Exception;
    
    Property boostProperty(BoostRequestDTO boostRequest, String userEmail) throws Exception;
}
