package com.lia2.lia2_backend.entity;

import jakarta.persistence.*;
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
