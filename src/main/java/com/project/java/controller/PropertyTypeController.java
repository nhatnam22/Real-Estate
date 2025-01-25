package com.project.java.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.model.PropertyType;
import com.project.java.responses.propertyTypeResponse.PropertyTypeResponse;
import com.project.java.service.PropertyTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/property/property-type")
@RequiredArgsConstructor
public class PropertyTypeController {

    private final PropertyTypeService propertyTypeService;

    @GetMapping
    public ResponseEntity<List<PropertyTypeResponse>> getAllPropertyType() throws Exception {
    	List<PropertyType> listType = propertyTypeService.getAllPropertyType();

        List<PropertyTypeResponse> list = listType.stream()
                .map(item -> new PropertyTypeResponse.Builder()
                        .image(item.getImage())
                        .name(item.getName())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(list);
    }

	public PropertyTypeController(PropertyTypeService propertyTypeService) {
		super();
		this.propertyTypeService = propertyTypeService;
	}
}
