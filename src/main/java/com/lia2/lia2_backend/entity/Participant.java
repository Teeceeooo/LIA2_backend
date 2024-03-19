package com.lia2.lia2_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Participant {
    @Id
    @Column(name = "participant_id")
    private String id;
    @Column(length = 50)
    private String fullName;
    @Column(length = 12)
    private String telephoneNumber;
    @Column(length = 5000)
    private String comment;
    @OneToOne(fetch = FetchType.EAGER)
    // insertable and updatable = false so Hibernate doesn't modify the table
    @JoinColumn(name = "image_id")
    private Image image;
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Item> participantItems = new ArrayList<>();


    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Activity> activityList = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Participant{id=" + id + ", fullName='" + fullName + "'}";
    }
}
