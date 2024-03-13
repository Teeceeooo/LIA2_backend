package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Activity;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.service.ActivityService;
import com.lia2.lia2_backend.service.ParticipantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/activity")
@CrossOrigin("*")
public class ActivityController {

    private final ActivityService activityService;

    private final ParticipantService participantService;


    @Autowired
    public ActivityController(ActivityService activityService, ParticipantService participantService) {
        this.activityService = activityService;
        this.participantService = participantService;

    }

    @GetMapping("/getLatest/{participantId}")
    public ResponseEntity<Activity> getLatestActivity(@PathVariable String participantId) {
        Activity activity = activityService.getLatestActivity(participantId);
        if (activity != null) {
            return ResponseEntity.ok(activity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getParticipantLogs")
    public ResponseEntity<List<Activity>> getParticipantActivities(@RequestParam String participantId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activities = activityService.getParticipantActivities(participantId,pageable);
        return ResponseEntity.ok().body(activities.getContent());
    }

    @GetMapping("/logs/{participantId}")
    public List<Activity> getActivitiesById(@PathVariable String participantId){
        return activityService.getActivitiesById(participantId);
    }

    @GetMapping("/logs")
    public List<Activity> getAllLogs() {
        return activityService.getAllLogs();
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Activity> ReponseEnticreateParticipant(@RequestBody Map<String, Object> newActivityData) {
        Map<String, String> participantData = (Map<String, String>) newActivityData.get("Participant");
        String participantId = participantData.get("id").toString();
        String typeOfActivity = newActivityData.get("typeOfActivity").toString();
        Activity newActivity = new Activity();
        try {
            Participant currentParticipant = participantService.getParticipantById(participantId);
            newActivity.setParticipant(currentParticipant);
            newActivity.setTypeOfActivity(Activity.TypeOfActivity.valueOf(typeOfActivity));

            LocalDateTime timeNow = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = timeNow.format(formatter);
            newActivity.setTimeOfActivity(formattedDateTime);

            activityService.createActivity(newActivity);

        }
        catch(Exception e) {
            System.out.println("Något gick fel: " + e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newActivity);
    }
}
