package com.assignment.userdetails.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.userdetails.model.UserDetails;
import com.assignment.userdetails.repository.UserDetailsRepository;

@Service
public class UserDetailsService {

	@Autowired
    private UserDetailsRepository userDetailsRepository;
	
	@Autowired
    private FileService fileService;

    public UserDetails saveUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
    
    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }
    
    public UserDetails updateUserDetails(Long userId, String newMobileNumber, String newEmail) {
        Optional<UserDetails> optionalUserDetails = userDetailsRepository.findById(userId);

        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            userDetails.setMobileNo(newMobileNumber);
            userDetails.setEmailId(newEmail);
            return userDetailsRepository.save(userDetails);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    
    public void deleteUserById(Long userId) {
        Optional<UserDetails> optionalUser = userDetailsRepository.findById(userId);

        if (optionalUser.isPresent()) {
            userDetailsRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
}

