package com.rotaract.project.repository;

import com.rotaract.project.domain.Club;
import com.rotaract.project.domain.Member;
import com.rotaract.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findProjectByClub(Pageable page, Club club);

}
