package com.lia2.lia2_backend.service;

import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Participant getParticipantById(int id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Användare med ID " + id + " finns inte."));
    }
    public Participant createParticipant(Participant participant) {
        // We can add validation logic here if needed
        return participantRepository.save(participant);
    }
    public void deleteParticipantById(int id) {
        if(!participantRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Användare med ID " + id + " finns inte.");
        }
        participantRepository.deleteById(id);
    }
}
