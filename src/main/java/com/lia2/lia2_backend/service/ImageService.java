package com.lia2.lia2_backend.service;

import com.lia2.lia2_backend.entity.Image;
import com.lia2.lia2_backend.repository.ImageRepository;
import com.lia2.lia2_backend.repository.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${upload.directory}")
    private String uploadDirectory;

    private final ImageRepository imageRepository;
    @Autowired ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<Resource> getImageBytes(String imageName) throws IOException {
        System.out.println("getImagesBytes");
        String imagePath = uploadDirectory + imageName;
        File imageFile = new File(imagePath);
        System.out.println("Imagepath: " + imagePath);

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
            System.out.println("Image not found");
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

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File destFile = new File(uploadDirectory + File.separator + uniqueFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

        return uniqueFileName;
    }
    @Transactional
    public int saveImageToDatabase(String imageUrl) {
       System.out.println("saveImageToDatabase KÖRS");
        Image image = new Image();
        image.setImageUrl(imageUrl);
        Image savedImage = imageRepository.save(image);
        return savedImage.getId();
    }

    @Transactional
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }


}
