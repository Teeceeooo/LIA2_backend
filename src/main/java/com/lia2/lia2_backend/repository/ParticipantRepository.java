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

    @Query("SELECT p FROM Participant p WHERE " +
            "(COALESCE(:fullName, '') = '' OR p.fullName LIKE %:fullName%) AND " +
            "(COALESCE(:telephoneNumber, '') = '' OR p.telephoneNumber LIKE %:telephoneNumber%) AND " +
            "(COALESCE(:comment, '') = '' OR p.comment LIKE %:comment%) AND " +
            "(:id IS NULL OR p.id = :id)")
    List<Participant> searchParticipants(@Param("fullName") String fullName,
                                         @Param("telephoneNumber") String telephoneNumber,
                                         @Param("comment") String comment,
                                         @Param("id") Integer id);
}
