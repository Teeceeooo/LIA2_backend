package com.lia2.lia2_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @Column(name = "item_id")
    private int id;
    private String description;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

}
