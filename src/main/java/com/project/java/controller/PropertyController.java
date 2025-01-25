package com.project.java.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.multipart.MultipartFile;

import com.project.java.dto.PropertyDTO;
import com.project.java.model.Property;
import com.project.java.model.User;
import com.project.java.responses.ClassResponse;
import com.project.java.service.IPropertyService;
import com.project.java.service.PropertyApprovalService;
import com.project.java.service.UploadFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("property")
public class PropertyController {
	private final PropertyApprovalService propertyApprovalService;
	private final IPropertyService iPropertyService;
	private final UploadFile uploadFile;
	
	@PostMapping("/post-property")
	public ResponseEntity<ClassResponse> postProperty(@Valid @RequestBody PropertyDTO propertyDTO, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.toList();
			return ResponseEntity.badRequest().body(ClassResponse.builder().status(HttpStatus.BAD_REQUEST).message("Lỗi nhập liệu: " + String.join(", ", errorMessages)).build());
		}
		Property newProperty = iPropertyService.createProperty(propertyDTO);
//		if(propertyApprovalService.isEligibleForAutoVerification(newProperty) == false) {
//			return ResponseEntity.badRequest().body(ClassResponse.builder().status(HttpStatus.ACCEPTED).message("Bài đăng đang chờ duyệt").build());
//		}
		return ResponseEntity.ok().body(ClassResponse.builder().status(HttpStatus.OK).message("Bài đăng đã được phê duyệt tự động").data(newProperty).build());
		
	}
	@PostMapping("/upload/")
	public ResponseEntity<ClassResponse> postImages(@ModelAttribute List<MultipartFile> files )
			throws Exception {
		if(uploadFile.isFileSizeWithinLimit(files)) {
			List<String> urlImages = files.stream()
				    .map(file -> {
				        try {
				            return uploadFile.storeFile(file);
				        } catch (Exception e) {
				            throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
				        }
				    })
				    .toList();
			return ResponseEntity.ok().body(ClassResponse.builder().data(urlImages).message("đăng tải hình ảnh thành công").status(HttpStatus.OK).build());
		}
		return ResponseEntity.badRequest().body(ClassResponse.builder().status(HttpStatus.BAD_REQUEST).message("không thể đăng tải hình ảnh").build());
	
	}
	public PropertyController(PropertyApprovalService propertyApprovalService, IPropertyService iPropertyService,
			UploadFile uploadFile) {
		super();
		this.propertyApprovalService = propertyApprovalService;
		this.iPropertyService = iPropertyService;
		this.uploadFile = uploadFile;
	}
}
