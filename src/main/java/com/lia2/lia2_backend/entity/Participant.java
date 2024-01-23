package com.lia2.lia2_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    // insertable and updatable = false so Hibernate doesn't modify the table
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private Image image;
    @OneToMany(mappedBy = "participant")
    @JsonManagedReference
    private List<Item> participantItems;
}
