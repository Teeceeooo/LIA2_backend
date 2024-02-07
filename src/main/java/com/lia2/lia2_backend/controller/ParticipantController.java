package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/participants")
@CrossOrigin("*")
public class ParticipantController {
    private final ParticipantService participantService;
    @Autowired
    private ItemController itemController;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Participant>> getAllParticipants() {
        List<Participant> participants = participantService.getAllParticipants();
        return ResponseEntity.ok(participants);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable int id) {
        Participant participant = participantService.getParticipantById(id);

        if (participant != null) {
            return ResponseEntity.ok(participant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
        Participant createdParticipant = participantService.createParticipant(participant);
        for (Item item : participant.getParticipantItems()) {
            item.setParticipant(createdParticipant);
            itemController.createItem(item);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipantById(@PathVariable int id) {
        try {
            participantService.deleteParticipantById(id);
            return ResponseEntity.ok("Användare med ID " + id + " raderad.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ett fel uppstod.");
        }
    }
}
