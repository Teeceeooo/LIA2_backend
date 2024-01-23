package com.lia2.lia2_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @Column(name = "image_id")
    private int id;
    @Column(name = "image_url")
    private String imageUrl;
}
