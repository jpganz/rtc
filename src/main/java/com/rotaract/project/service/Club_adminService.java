package com.rotaract.project.service;

import com.rotaract.project.domain.Club_admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Club_admin.
 */
public interface Club_adminService {

    /**
     * Save a club_admin.
     *
     * @param club_admin the entity to save
     * @return the persisted entity
     */
    Club_admin save(Club_admin club_admin);

    /**
     * Get all the club_admins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Club_admin> findAll(Pageable pageable);


    /**
     * Get the "id" club_admin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Club_admin> findOne(Long id);

    Optional<Club_admin> findByUserId(Long id);

    /**
     * Delete the "id" club_admin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
