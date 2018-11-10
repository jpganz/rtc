package com.rotaract.project.service.impl;

import com.rotaract.project.domain.Club;
import com.rotaract.project.domain.User;
import com.rotaract.project.repository.UserRepository;
import com.rotaract.project.service.MemberService;
import com.rotaract.project.domain.Member;
import com.rotaract.project.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Member.
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public MemberServiceImpl(MemberRepository memberRepository, UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a member.
     *
     * @param member the entity to save
     * @return the persisted entity
     */
    @Override
    public Member save(Member member) {
        log.debug("Request to save Member : {}", member);        return memberRepository.save(member);
    }

    /**
     * Get all the members.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Member> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return memberRepository.findAllByDeleted(pageable, 0);
    }


    /**
     * Get one member by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        return memberRepository.findById(id);
    }

    /**
     * Delete the member by id.
     *
     * @param id the id of the entity
     */

    //todo: fix this nasty workaround
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        try{
            Member member = memberRepository.getOne(id);
            Optional<User> user = userRepository.findOneByMember(member);
            user.ifPresent( u -> {
                user.get().setDeleted(1);
                userRepository.save(user.get());
            });
            member.setDeleted(1);
            memberRepository.save(member);
        }catch(Exception e){
            log.error("Could not delete member - user association for id: {}", id);
        }
        //memberRepository.deleteById(id);
    }

    @Override
    public Page<Member> findByClub(Pageable page, Club club) {
        return memberRepository.findMemberByClubAndDeleted(page, club, 0);
    }
}
