package com.rotaract.project.service;

import com.rotaract.project.domain.Club;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Club.
 */
public interface ClubService {

    /**
     * Save a club.
     *
     * @param club the entity to save
     * @return the persisted entity
     */
    Club save(Club club);

    /**
     * Get all the clubs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Club> findAll(Pageable pageable);


    /**
     * Get the "id" club.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Club> findOne(Long id);

    /**
     * Delete the "id" club.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
