package com.assignment.userdetails.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.userdetails.model.FileEntity;
import com.assignment.userdetails.model.UserDetails;
import com.assignment.userdetails.repository.FileRepository;
import com.assignment.userdetails.repository.UserDetailsRepository;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public String saveFile(MultipartFile file, Long userId) throws IOException {
    	UserDetails userDetails = userDetailsRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    	
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = "/userdetails/uploaded-files/" + fileName;

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(filePath);
        fileEntity.setFileData(file.getBytes());
        fileEntity.setUserDetails(userDetails);
        fileRepository.save(fileEntity);
        
        return filePath;
    }
}
