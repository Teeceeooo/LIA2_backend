package com.lia2.lia2_backend.repository;

import com.lia2.lia2_backend.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop1ByParticipantIdOrderByTimeOfActivityDesc(String participantId);

    Page<Activity> findByParticipantId(String participantId, Pageable pageable);

    Activity findFirstByParticipantIdOrderByIdDesc(String participantId);
}