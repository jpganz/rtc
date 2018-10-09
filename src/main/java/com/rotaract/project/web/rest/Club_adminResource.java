package com.rotaract.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rotaract.project.domain.Club_admin;
import com.rotaract.project.service.Club_adminService;
import com.rotaract.project.web.rest.errors.BadRequestAlertException;
import com.rotaract.project.web.rest.util.HeaderUtil;
import com.rotaract.project.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Club_admin.
 */
@RestController
@RequestMapping("/api")
public class Club_adminResource {

    private final Logger log = LoggerFactory.getLogger(Club_adminResource.class);

    private static final String ENTITY_NAME = "club_admin";

    private final Club_adminService club_adminService;

    public Club_adminResource(Club_adminService club_adminService) {
        this.club_adminService = club_adminService;
    }

    /**
     * POST  /club-admins : Create a new club_admin.
     *
     * @param club_admin the club_admin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new club_admin, or with status 400 (Bad Request) if the club_admin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/club-admins")
    @Timed
    public ResponseEntity<Club_admin> createClub_admin(@Valid @RequestBody Club_admin club_admin) throws URISyntaxException {
        log.debug("REST request to save Club_admin : {}", club_admin);
        if (club_admin.getId() != null) {
            throw new BadRequestAlertException("A new club_admin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Club_admin result = club_adminService.save(club_admin);
        return ResponseEntity.created(new URI("/api/club-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /club-admins : Updates an existing club_admin.
     *
     * @param club_admin the club_admin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated club_admin,
     * or with status 400 (Bad Request) if the club_admin is not valid,
     * or with status 500 (Internal Server Error) if the club_admin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/club-admins")
    @Timed
    public ResponseEntity<Club_admin> updateClub_admin(@Valid @RequestBody Club_admin club_admin) throws URISyntaxException {
        log.debug("REST request to update Club_admin : {}", club_admin);
        if (club_admin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Club_admin result = club_adminService.save(club_admin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, club_admin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /club-admins : get all the club_admins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of club_admins in body
     */
    @GetMapping("/club-admins")
    @Timed
    public ResponseEntity<List<Club_admin>> getAllClub_admins(Pageable pageable) {
        log.debug("REST request to get a page of Club_admins");
        Page<Club_admin> page = club_adminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/club-admins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /club-admins/:id : get the "id" club_admin.
     *
     * @param id the id of the club_admin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the club_admin, or with status 404 (Not Found)
     */
    @GetMapping("/club-admins/{id}")
    @Timed
    public ResponseEntity<Club_admin> getClub_admin(@PathVariable Long id) {
        log.debug("REST request to get Club_admin : {}", id);
        Optional<Club_admin> club_admin = club_adminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(club_admin);
    }

    /**
     * DELETE  /club-admins/:id : delete the "id" club_admin.
     *
     * @param id the id of the club_admin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/club-admins/{id}")
    @Timed
    public ResponseEntity<Void> deleteClub_admin(@PathVariable Long id) {
        log.debug("REST request to delete Club_admin : {}", id);
        club_adminService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
