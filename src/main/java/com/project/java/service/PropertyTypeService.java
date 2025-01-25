package com.project.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.java.model.PropertyType;

@Service
public interface PropertyTypeService {
	public List<PropertyType> getAllPropertyType() throws Exception;
}
