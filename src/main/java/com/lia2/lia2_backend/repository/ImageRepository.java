package com.lia2.lia2_backend.repository;

import com.lia2.lia2_backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
