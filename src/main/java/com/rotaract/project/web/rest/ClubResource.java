package com.rotaract.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rotaract.project.domain.Club;
import com.rotaract.project.domain.Club_admin;
import com.rotaract.project.domain.User;
import com.rotaract.project.security.AuthoritiesConstants;
import com.rotaract.project.service.ClubService;
import com.rotaract.project.service.Club_adminService;
import com.rotaract.project.service.UserService;
import com.rotaract.project.web.rest.errors.BadRequestAlertException;
import com.rotaract.project.web.rest.util.HeaderUtil;
import com.rotaract.project.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Club.
 */
@RestController
@RequestMapping("/api")
public class ClubResource {

    private final Logger log = LoggerFactory.getLogger(ClubResource.class);

    private static final String ENTITY_NAME = "club";

    private final ClubService clubService;
    private final Club_adminService clubAdminService;
    private final UserService userService;

    public ClubResource(ClubService clubService, Club_adminService clubAdminService, UserService userService) {
        this.clubService = clubService;
        this.clubAdminService = clubAdminService;
        this.userService = userService;
    }

    /**
     * POST  /clubs : Create a new club.
     *
     * @param club the club to create
     * @return the ResponseEntity with status 201 (Created) and with body the new club, or with status 400 (Bad Request) if the club has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clubs")
    @Timed
    public ResponseEntity<Club> createClub(@Valid @RequestBody Club club) throws URISyntaxException {
        log.debug("REST request to save Club : {}", club);
        //1 check if there is no previous club created

        if (club.getId() != null) {
            throw new BadRequestAlertException("A new club cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Club result = clubService.save(club);
        return ResponseEntity.created(new URI("/api/clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clubs : Updates an existing club.
     *
     * @param club the club to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated club,
     * or with status 400 (Bad Request) if the club is not valid,
     * or with status 500 (Internal Server Error) if the club couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clubs")
    @Timed
    public ResponseEntity<Club> updateClub(@Valid @RequestBody Club club) throws URISyntaxException {
        log.debug("REST request to update Club : {}", club);
        if (club.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Club result = clubService.save(club);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, club.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clubs : get all the clubs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clubs in body
     */
    @GetMapping("/clubs")
    @Timed
    public ResponseEntity<List<Club>> getAllClubs(Pageable pageable) {
        log.debug("REST request to get a page of Clubs");
        // get only related clubs
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String name = auth.getName();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        Page<Club> page = new PageImpl<Club>(new ArrayList<>());
        if(isClubAdmin(authorities)){
            //call club admin by user
            Optional<User> user = userService.getUserWithAuthoritiesByLogin(name);


            Optional<Club_admin> clubAdmin = clubAdminService.findByUserId(user.get().getId());
            //get club id
            List<Club> clubs = new ArrayList<>();
            if(clubAdmin.isPresent()){
                clubs.add(clubService.findOne(clubAdmin.get().getClub().getId()).get());
            }
            page = new PageImpl<>(clubs);
        }else{
            //page = clubService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clubs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clubs/:id : get the "id" club.
     *
     * @param id the id of the club to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the club, or with status 404 (Not Found)
     */
    @GetMapping("/clubs/{id}")
    @Timed
    public ResponseEntity<Club> getClub(@PathVariable Long id) {
        log.debug("REST request to get Club : {}", id);
        Optional<Club> club = clubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(club);
    }

    /**
     * DELETE  /clubs/:id : delete the "id" club.
     *
     * @param id the id of the club to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clubs/{id}")
    @Timed
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        log.debug("REST request to delete Club : {}", id);
        clubService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private boolean isClubAdmin(Collection<SimpleGrantedAuthority> authorities){
        for(SimpleGrantedAuthority authority:authorities){
            System.out.println(authority.getAuthority());
            if("CLUB_ADMIN".equals(authority.getAuthority())){
                return true;
            }
        }
        return false;
        //return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_CLUB_ADMIN")).findFirst().isPresent();
    }

}
