package com.project.java.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFile {
	private static final String UPLOADS_FOLDER = "uploads";
	public Boolean isFileSizeWithinLimit (List<MultipartFile> files) throws Exception {
		files = files == null? new ArrayList<MultipartFile>(): files;
		if(files.size() > 5) {
			throw new Exception("Số lượng file tải lên không được vượt quá 5");
		}
		for (MultipartFile file : files) {
			if( file.getSize() == 0) {
				continue;
			}
			if( file.getSize() > 10*1024*1024) {
				throw new Exception("Kích thước mỗi file không được vượt quá 10MB.");
			}
		}
		return true;
			
	}
	public String storeFile (MultipartFile file) throws IOException{
		String fileName ="" ;
		if(file.getOriginalFilename() != null) {
			fileName = StringUtils.cleanPath(file.getOriginalFilename());// clean name file and remove invalid character 
		}
		if(fileName.isEmpty()) {
			throw new IllegalArgumentException("Invalid filename or filename is empty");// check when remove invalid character, file name í valid
		}
		String uniqueFileName = UUID.randomUUID().toString()+fileName;
		Path uploadDir = Paths.get(UPLOADS_FOLDER);
		if(!Files.exists(uploadDir)) {
			Files.createDirectories(uploadDir);
		}
	       Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
	        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
	        return uniqueFileName;
	}
	
    public void deleteFile(String filename) throws IOException {
        Path uploadDir = Paths.get(UPLOADS_FOLDER);
        Path filePath = uploadDir.resolve(filename);
        if(Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new FileNotFoundException("File not found: " + filename);
        }
    }
}
