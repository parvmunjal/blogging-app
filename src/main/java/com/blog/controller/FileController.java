package com.blog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.payload.FileResponse;
import com.blog.services.FileService;

@RestController
@RequestMapping("/api/")
public class FileController {
	@Autowired
	private FileService fileService;
	
	String path="src/main/resources/static/images/";
	//upload file
	@PostMapping("/test/upload")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			){
		String fileName=null;
		try {
			fileName = fileService.uploadImage(path, image);
			
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<FileResponse>(new FileResponse(null,"Image not uploaded,an error occured!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<FileResponse>(new FileResponse(fileName,"Image uploaded successfully!"),HttpStatus.OK);
	}
	
	
}
