package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Image;
import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.service.ImageService;
import com.lia2.lia2_backend.service.ParticipantService;
import jakarta.transaction.Transactional;
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
    private ImageService imageService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Transactional
    @PutMapping("/edit")
    public Participant editParticipant(@RequestBody Participant editedParticipant) {
        Participant existingParticipant = participantService.getParticipantById(editedParticipant.getId());

        if (existingParticipant != null) {
            if (editedParticipant.getImage() != null) {
                Image participantImage = editedParticipant.getImage();
                imageService.saveImage(participantImage);
                existingParticipant.setImage(participantImage);
                //System.out.println("Har nya bilden OK ID/URL: " + participantImage.getId() + " " + participantImage.getImageUrl());
            }

            for (Item item : existingParticipant.getParticipantItems()) {
                itemController.deleteItemById(item.getId());
            }

            for (Item item : editedParticipant.getParticipantItems()) {
                item.setParticipant(editedParticipant);
               // System.out.println("Detta är itemet: " + item.getDescription());
                itemController.createItem(item);
            }

            existingParticipant.setFullName(editedParticipant.getFullName());
            existingParticipant.setTelephoneNumber(editedParticipant.getTelephoneNumber());
            existingParticipant.setComment(editedParticipant.getComment());
            List<Item> newItems = editedParticipant.getParticipantItems();
            existingParticipant.setParticipantItems(newItems);

            System.out.println("Den nya participanten:  " + existingParticipant);
            return participantService.editParticipant(existingParticipant);
        } else {
            return null;
        }

    }

    @GetMapping("/findById/{id}")
    public Boolean checkIfExist(@PathVariable int id) {
        return participantService.checkIfExist(id);
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
        if (participant.getImage() != null) {
            Image participantImage = participant.getImage();
            imageService.saveImage(participantImage);
        }

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

