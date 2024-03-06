package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Image;
import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.service.ImageService;
import com.lia2.lia2_backend.service.ItemService;
import com.lia2.lia2_backend.service.ParticipantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/participants")
@CrossOrigin("*")
public class ParticipantController {
    private final ParticipantService participantService;

    private ItemController itemController;

    private ImageService imageService;

    private final ItemService itemService;

    @Autowired
    public ParticipantController(ParticipantService participantService, ItemService itemService, ImageService imageService, ItemController itemController) {
        this.participantService = participantService;
        this.itemService = itemService;
        this.imageService = imageService;
        this.itemController = itemController;
    }


    @GetMapping("/findById/{id}")
    public Boolean checkIfExist(@PathVariable String id) {
        return participantService.checkIfExist(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable String id) {
        Participant participant = participantService.getParticipantById(id);
        if (participant != null) {
            return ResponseEntity.ok(participant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {

        if(participant.getImage() != null){
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

    @Transactional
    @PutMapping("/edit")
    public ResponseEntity<Participant> editParticipant(@RequestBody Participant participant) {
        Participant exisintParticipant = participantService.getParticipantById(participant.getId());
        if(exisintParticipant.getParticipantItems() != null){
            for(Item item : exisintParticipant.getParticipantItems()){
                itemService.deleteItemById(item.getId());
            }
        }

        if (participant.getImage() != null) {
            Image participantImage = participant.getImage();
            imageService.saveImage(participantImage);
        }

        Participant createdParticipant = participantService.createParticipant(participant);
        for (Item item : participant.getParticipantItems()) {
            item.setParticipant(createdParticipant);
            itemService.createItem(item);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipantById(@PathVariable String id) {
        try {
            participantService.deleteParticipantById(id);
            return ResponseEntity.ok("Användare med ID " + id + " raderad.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ett fel uppstod.");
        }
    }

    @PostMapping("/searchusers")
    public List<Participant> searchParticipants(@RequestBody Map<String, String> searchData) {
        String fullName = searchData.get("fullName");
        String telephoneNumber = searchData.get("telephoneNumber");
        String comment = searchData.get("comment");
        String id = searchData.get("id");
        return participantService.searchParticipants(fullName, telephoneNumber, comment, id);
    }
}

