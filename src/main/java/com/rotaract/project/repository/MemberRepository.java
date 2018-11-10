package com.rotaract.project.repository;

import com.rotaract.project.domain.Club;
import com.rotaract.project.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findMemberByClubAndDeleted(Pageable page, Club club ,int deleted);

    Page<Member> findAllByDeleted(Pageable page,int deleted);

    Optional<Member> findByIdAndDeleted(Long id, int deleted);
}
