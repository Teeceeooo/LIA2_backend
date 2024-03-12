package com.lia2.lia2_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable=true)
    @JsonBackReference
    private Participant participant;

    public enum TypeOfActivity {
        CHECKED_IN,
        CHECKED_OUT,
        LEFT_THE_BUILDING,
        ENTERED_THE_BUILDING
    }

    private String timeOfActivity;

    @Enumerated(EnumType.STRING)
    private TypeOfActivity typeOfActivity;

}
