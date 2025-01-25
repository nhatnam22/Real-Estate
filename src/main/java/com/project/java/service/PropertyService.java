package com.project.java.service;

import org.springframework.stereotype.Service;
import com.project.java.dto.PropertyDTO;
import com.project.java.model.Property;

@Service
public interface PropertyService {

    Property createProperty(PropertyDTO propertyDTO) throws Exception;
    
    boolean verifyPost(PropertyDTO propertyDTO) throws Exception;
    
    Property updateProperty(PropertyDTO propertyDTO) throws Exception;
    
    void deleteProperty(Long id) throws Exception;
}
