package com.lia2.lia2_backend.service;

import com.lia2.lia2_backend.entity.Activity;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ParticipantService participantService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, ParticipantService participantService) {
        this.activityRepository = activityRepository;
        this.participantService = participantService;
    }

    public Page<Activity> getParticipantActivities(String participantId, Pageable pageable) {
        return activityRepository.findByParticipantId(participantId, pageable);
    }

    public Activity createActivity(Activity newActivity) {
        return activityRepository.save(newActivity);
    }

    public List<Activity> getAllLogs() {
        System.out.println("SERVICE");
        return activityRepository.findAll();
    }

    public List<Activity> getActivitiesById(String participantId) {
        return activityRepository.findTop1ByParticipantIdOrderByTimeOfActivityDesc(participantId);
    }

    public Activity getLatestActivity(String id) {
        return activityRepository.findFirstByParticipantIdOrderByIdDesc(id);
    }
}
