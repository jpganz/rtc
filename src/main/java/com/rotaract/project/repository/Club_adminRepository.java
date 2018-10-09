package com.rotaract.project.repository;

import com.rotaract.project.domain.Club_admin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Club_admin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Club_adminRepository extends JpaRepository<Club_admin, Long> {

    @Query("select club_admin from Club_admin club_admin where club_admin.user.login = ?#{principal.username}")
    List<Club_admin> findByUserIsCurrentUser();

    Optional<Club_admin> findClub_adminByUserId(Long userId);
}
