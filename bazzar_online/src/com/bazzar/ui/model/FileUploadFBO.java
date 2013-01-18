package com.bazzar.ui.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadFBO {
	MultipartFile file;
	String originalFileName = null;
	    
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
}
