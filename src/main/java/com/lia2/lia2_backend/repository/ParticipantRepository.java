package com.lia2.lia2_backend.repository;

import com.lia2.lia2_backend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {

    @Query("SELECT p FROM Participant p WHERE " +
            "(COALESCE(:fullName, '') = '' OR p.fullName LIKE %:fullName%) AND " +
            "(COALESCE(:telephoneNumber, '') = '' OR p.telephoneNumber LIKE %:telephoneNumber%) AND " +
            "(COALESCE(:comment, '') = '' OR p.comment LIKE %:comment%) AND " +
            "(:id IS NULL OR p.id = :id)")
    List<Participant> searchParticipants(@Param("fullName") String fullName,
                                         @Param("telephoneNumber") String telephoneNumber,
                                         @Param("comment") String comment,
                                         @Param("id") String id);

    @Query("SELECT COUNT(p) FROM Participant p " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM Activity a " +
            "   WHERE a.participant = p AND a.typeOfActivity = 'CHECKED_OUT'" +
            ")")
    int countCheckedInParticipants();

    @Query("SELECT COUNT(p) FROM Participant p " +
            "JOIN p.activityList a " +
            "WHERE a.id IN (" +
            "   SELECT MAX(a2.id) FROM Activity a2 " +
            "   WHERE a2.participant = p" +
            ") AND (a.typeOfActivity = 'ENTERED_THE_BUILDING' OR a.typeOfActivity = 'CHECKED_IN')")
    int countNumberOfParticipantsInBuilding();

}



