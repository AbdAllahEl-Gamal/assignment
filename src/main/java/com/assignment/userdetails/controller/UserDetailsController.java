package com.assignment.userdetails.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.userdetails.model.UserDetails;
import com.assignment.userdetails.service.FileService;
import com.assignment.userdetails.service.UserDetailsService;

@RestController
@RequestMapping("/api/userdetails")
@CrossOrigin(origins = "http://localhost:3000")
public class UserDetailsController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private FileService fileService;
	
	@PostMapping("/save")
    public UserDetails saveUserDetails(@RequestParam("passport") MultipartFile passport,
            @RequestParam("photo") MultipartFile photo, 
            @RequestBody UserDetails userDetails) throws IOException {
		String passportPath = fileService.saveFile(passport, userDetails.getId());
		String photoPath = fileService.saveFile(photo, userDetails.getId());
		userDetails.setPassportPath(passportPath);
		userDetails.setUserPhotoPath(photoPath);
        return userDetailsService.saveUser(userDetails);
    }
	
	@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                            @PathVariable Long userId) {
        try {
        	fileService.saveFile(file, userId);
        	return ResponseEntity.ok("File uploaded successfully.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
		}
    }
	
	@GetMapping
    public ResponseEntity<List<UserDetails>> getAllUserDetails() {
        List<UserDetails> userDetails = userDetailsService.getAllUserDetails();
        return ResponseEntity.ok(userDetails);
    }
	
	@PutMapping("/update/{userId}")
    public ResponseEntity<UserDetails> updateUserContactDetails(
            @PathVariable Long userId,
            @RequestParam(required = false) String newMobileNumber,
            @RequestParam(required = false) String newEmail) {
		System.out.println("Test");
		System.out.println(newMobileNumber);
		System.out.println(newEmail);
        UserDetails updatedUser = userDetailsService.updateUserDetails(userId, newMobileNumber, newEmail);
        return ResponseEntity.ok(updatedUser);
    }
	
	@DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        userDetailsService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
	
}