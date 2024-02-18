package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Image;
import com.lia2.lia2_backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Controller
@RequestMapping("/api/v1/images")
@CrossOrigin("*")
public class ImageController {
    private final ImageService imageService;

    @Value("C:/LIA2_backend/src/main/resources/img")
    private String uploadDirectory;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/img/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        imageService.determineImageType(imageName);
        return imageService.getImageBytes(imageName);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("KÖRS JAG?!");
            String fileName = imageService.uploadImage(file);
            String imageUrl = imageService.getImageUrl(fileName);
            //int imageId = imageService.saveImageToDatabase(fileName);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Ladda upp en bild, tack.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kunde inte ladda upp.");
        }
    }
}
