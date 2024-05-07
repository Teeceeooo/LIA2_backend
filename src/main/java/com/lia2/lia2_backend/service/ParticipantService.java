package com.lia2.lia2_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.repository.ImageRepository;
import com.lia2.lia2_backend.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    private final ImageRepository imageRepository;


    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, ImageRepository imageRepository) {
        this.participantRepository = participantRepository;
        this.imageRepository = imageRepository;
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Participant getParticipantById(String id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Användare med ID " + id + " finns inte."));
    }

    public Participant createParticipant(Participant participant) {
        // We can add validation logic here if needed
        return participantRepository.save(participant);
    }
    public void deleteParticipantById(String id) {
        if(!participantRepository.existsById(id)) {
            throw new EntityNotFoundException("Användare med ID " + id + " finns inte.");
        }
        participantRepository.deleteById(id);
    }

    public Boolean checkIfExist(String id) {
        return participantRepository.existsById(id);
    }

    public Participant editParticipant(Participant editedParticipant) {
        if(editedParticipant.getImage() != null) {
            imageRepository.save(editedParticipant.getImage());
        }
       return participantRepository.save(editedParticipant);
    }



    public List<Participant> searchParticipants(String fullName, String telephoneNumber, String comment, String id) {
        return participantRepository.searchParticipants(fullName, telephoneNumber, comment, id);
    }

    public int findNumberOfParticipants() {
        return participantRepository.countCheckedInParticipants();
    }


    public int countParticipantsInBuilding() {
        return participantRepository.countNumberOfParticipantsInBuilding();
    }
}
