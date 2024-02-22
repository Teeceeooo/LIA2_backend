package com.lia2.lia2_backend.repository;

import com.lia2.lia2_backend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    @Query("SELECT p FROM Participant p WHERE p.fullName LIKE %:fullName% OR p.telephoneNumber LIKE %:telephoneNumber%")
    List<Participant> searchParticipants(@Param("fullName") String fullName, @Param("telephoneNumber") String telephoneNumber);
}
