package com.FileHandling.FileHandling.controler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FileHandling.FileHandling.entity.FileData;
import com.FileHandling.FileHandling.repo.FileDataRepo;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class PracticeControler {
@Autowired
private FileDataRepo repo;
	@PostMapping("/upload")
	
	public ResponseEntity<String> Upload(@RequestParam MultipartFile file) {
		
		if (file.isEmpty()) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" NO file selected ");
		}
		
		try {
			
			String  directoryPath= "F://fullstack-backend//newone//";
			File  upladDirectory =new File(directoryPath); 
			
			if (!upladDirectory .exists()) {
				upladDirectory .mkdirs(); // Create the directory if it does not exist
	        }
			
			 
			String path =directoryPath+file.getOriginalFilename();
			
			file.transferTo(new File(path));

			
			FileData fileData = new FileData(file.getOriginalFilename(),path);
	           repo.save(fileData);
			
			return ResponseEntity.ok("File uploaded successfully");
		} catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
		
	
		
		
		
		
	}
	
	
	   @GetMapping("/get/{id}")
	    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws IOException {
	    	
	    	
	        FileData data = repo.findByIdEager(id)
	                            .orElseThrow(() -> new EntityNotFoundException("FileData not found"));
	
	        String path = data.getFilePath();
	        File file = new File(path);
	        
	
	    
	        Resource resource = new FileSystemResource(file);

	        // Determine the content type based on the file extension or content
	        String contentType = Files.probeContentType(file.toPath());
	         System.out.println(contentType);
	        return ResponseEntity.ok()
	                             .contentType(MediaType.parseMediaType(contentType))
	                             .body(resource);
	    }
	
		
}
