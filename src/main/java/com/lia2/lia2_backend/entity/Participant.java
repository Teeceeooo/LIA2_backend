package com.lia2.lia2_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Participant {
    @Id
    @Column(name = "participant_id")
    private int id;
    @Column(length = 50)
    private String fullName;
    @Column(length = 12)
    private String telephoneNumber;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @OneToMany(mappedBy = "participant")
    private List<Item> participantItems;
}
