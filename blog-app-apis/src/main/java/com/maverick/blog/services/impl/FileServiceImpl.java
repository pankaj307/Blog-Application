package com.maverick.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maverick.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		// File name
		String originalFileName = file.getOriginalFilename();
		
		// Random name generated for file
		String randomId = UUID.randomUUID().toString();
		String updatedFileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
		
		// Full path
		String filePath = path + File.separator + updatedFileName;
		
		// Create folder if not exist
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		
		// File copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return updatedFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
