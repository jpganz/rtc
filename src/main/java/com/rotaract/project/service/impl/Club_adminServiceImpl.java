package com.rotaract.project.service.impl;

import com.rotaract.project.service.Club_adminService;
import com.rotaract.project.domain.Club_admin;
import com.rotaract.project.repository.Club_adminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Club_admin.
 */
@Service
@Transactional
public class Club_adminServiceImpl implements Club_adminService {

    private final Logger log = LoggerFactory.getLogger(Club_adminServiceImpl.class);

    private final Club_adminRepository club_adminRepository;

    public Club_adminServiceImpl(Club_adminRepository club_adminRepository) {
        this.club_adminRepository = club_adminRepository;
    }

    /**
     * Save a club_admin.
     *
     * @param club_admin the entity to save
     * @return the persisted entity
     */
    @Override
    public Club_admin save(Club_admin club_admin) {
        log.debug("Request to save Club_admin : {}", club_admin);        return club_adminRepository.save(club_admin);
    }

    /**
     * Get all the club_admins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Club_admin> findAll(Pageable pageable) {
        log.debug("Request to get all Club_admins");
        return club_adminRepository.findAll(pageable);
    }


    /**
     * Get one club_admin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Club_admin> findOne(Long id) {
        log.debug("Request to get Club_admin : {}", id);
        return club_adminRepository.findById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Club_admin> findByUserId(Long id) {
        log.debug("Request to get Club_admin : {}", id);
        Optional<Club_admin> admin = club_adminRepository.findClub_adminByUserId(id);

        if(admin.isPresent()){
            return Optional.of(admin.get());
        }
        return Optional.empty();
    }

    /**
     * Delete the club_admin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Club_admin : {}", id);
        club_adminRepository.deleteById(id);
    }
}
