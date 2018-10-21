package com.rotaract.project.repository;

import com.rotaract.project.domain.Club;
import com.rotaract.project.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findMemberByClub(Pageable page, Club club);

}
