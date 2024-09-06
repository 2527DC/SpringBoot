//package com.FileHandling.FileHandling.controler;
//
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.FileHandling.FileHandling.entity.FileData;
//import com.FileHandling.FileHandling.repo.FileDataRepo;
//
//import jakarta.persistence.EntityNotFoundException;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.Optional;
//
////@RestController
//public class FileUploadController {
//
//    @Autowired
//    private FileDataRepo repo;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected.");
//        }
//
//        try {
//            // Define a path where you want to save the file
//            String uploadDir = "F://fullstack-backend//FileFolder//";
//            File uploadDirectory = new File(uploadDir);
//            if (!uploadDirectory.exists()) {
//                uploadDirectory.mkdirs(); // Create the directory if it does not exist
//            }
//
//            // Save the file locally
//            String filePath = uploadDir + file.getOriginalFilename();
//            file.transferTo(new File(filePath));
//
//            // Save file details in the database
//            FileData fileData = new FileData(file.getOriginalFilename(), filePath);
//            repo.save(fileData);
//
//            return ResponseEntity.ok("File uploaded successfully and saved to database: " + filePath);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
//        }
//    }
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws IOException {
//
//
//        FileData data = repo.findByIdEager(id)
//                            .orElseThrow(() -> new EntityNotFoundException("FileData not found"));
//
//        String path = data.getFilePath();
//        File file = new File(path);
//
//
//        Resource resource = new FileSystemResource(file);
//
//        // Determine the content type based on the file extension or content
//        String contentType = Files.probeContentType(file.toPath());
//
//        return ResponseEntity.ok()
//                             .contentType(MediaType.parseMediaType(contentType))
//                             .body(resource);
//    }
//
//
//
//
//}
