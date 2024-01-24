package com.lia2.lia2_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ImageService {
    private static final String IMAGE_DIRECTORY = "src/main/resources/img/";

    @Autowired ImageService() {
    }

    public ResponseEntity<Resource> getImageBytes(String imageName) throws IOException {
        String imagePath = IMAGE_DIRECTORY + imageName;
        File imageFile = new File(imagePath);

        if(imageFile.exists()) {
            MediaType mediaType = determineImageType(imageName);

            byte[] imageBytes;
            try(FileInputStream fileInputStream = new FileInputStream(imageFile)) {
                imageBytes = FileCopyUtils.copyToByteArray(fileInputStream);
            }
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public MediaType determineImageType(String imageName) {
        if(imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (imageName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
