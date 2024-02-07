package com.lia2.lia2_backend.repository;

import com.lia2.lia2_backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
