package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Image;
import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.service.ImageService;
import com.lia2.lia2_backend.service.ItemService;
import com.lia2.lia2_backend.service.ParticipantService;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.core.io.NumberInput.parseInt;

@RestController
@RequestMapping("api/v1/participants")
@CrossOrigin("*")
public class ParticipantController {
    private final ParticipantService participantService;
    @Autowired
    private ItemController itemController;
    @Autowired
    private ImageService imageService;

    private final ItemService itemService;

    @Autowired
    public ParticipantController(ParticipantService participantService, ItemService itemService) {
        this.participantService = participantService;
        this.itemService = itemService;
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

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {


        Participant test = participantService.getParticipantById(participant.getId());
        if(test.getParticipantItems() != null){
            for(Item item : test.getParticipantItems()){
                itemService.deleteItemById(item.getId());
                System.out.println(item.getId());
            }
        }
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
        Participant test = participantService.getParticipantById(participant.getId());
        if(test.getParticipantItems() != null){
            for(Item item : test.getParticipantItems()){
                itemService.deleteItemById(item.getId());
                System.out.println(item.getId());
            }
        }

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

    @PostMapping("/searchusers")
    public List<Participant> searchParticipants(@RequestBody Map<String, Object> searchData) {
       // String id = searchData.get("id").toString();
        String fullName = searchData.get("fullName") != null ? searchData.get("fullName").toString() : null;
        String telephoneNumber = searchData.get("telephoneNumber") != null ? searchData.get("telephoneNumber").toString() : null;
        return participantService.searchParticipants(fullName, telephoneNumber);
    }
}

