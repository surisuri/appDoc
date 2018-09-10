package com.lkssoft.appDoc.com.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/hello")
public class FileUploadController {
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, 
						 @RequestParam("name") String fileName,
						 Model model) throws IllegalStateException, IOException {
		if(!file.isEmpty()) {
			File f = new File("c:\\upload", file.getOriginalFilename());
			file.transferTo(f);	
		}
		
		model.addAttribute("fileName", fileName);
		
		return "upload_ok";
	}
}
