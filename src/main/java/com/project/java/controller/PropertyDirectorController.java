package com.project.java.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.model.Direction;
import com.project.java.responses.ClassResponse;

@RestController
@RequestMapping("/property/direction")
public class PropertyDirectorController {


	@GetMapping("/get-all")
	public ResponseEntity<ClassResponse> getAllDirectionProperty() {
		System.out.println("Request to /get-all received");
		List<String> listDirection = Arrays.stream(Direction.values()).map(Enum::name).toList();
		return ResponseEntity.ok().body(ClassResponse.builder().status(HttpStatus.OK).message("have all direction").data(listDirection).build());
	}

}