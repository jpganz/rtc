package com.rotaract.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.rotaract.project.domain.enumeration.MemberStatusEnum;

import com.rotaract.project.domain.enumeration.GenderEnum;

import com.rotaract.project.domain.enumeration.PositionEnum;

import com.rotaract.project.domain.enumeration.CommitteeEnum;

import com.rotaract.project.domain.enumeration.ProfessionalAreaEnum;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatusEnum status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private PositionEnum position;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "committee", nullable = false)
    private CommitteeEnum committee;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "professional_area", nullable = false)
    private ProfessionalAreaEnum professional_area;

    @NotNull
    @Column(name = "starting_membership", nullable = false)
    private LocalDate starting_membership;

    @Column(name = "ending_membership")
    private LocalDate ending_membership;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Club club;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Member name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Member last_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public MemberStatusEnum getStatus() {
        return status;
    }

    public Member status(MemberStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(MemberStatusEnum status) {
        this.status = status;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public Member gender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Member birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public Member position(PositionEnum position) {
        this.position = position;
        return this;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public CommitteeEnum getCommittee() {
        return committee;
    }

    public Member committee(CommitteeEnum committee) {
        this.committee = committee;
        return this;
    }

    public void setCommittee(CommitteeEnum committee) {
        this.committee = committee;
    }

    public ProfessionalAreaEnum getProfessional_area() {
        return professional_area;
    }

    public Member professional_area(ProfessionalAreaEnum professional_area) {
        this.professional_area = professional_area;
        return this;
    }

    public void setProfessional_area(ProfessionalAreaEnum professional_area) {
        this.professional_area = professional_area;
    }

    public LocalDate getStarting_membership() {
        return starting_membership;
    }

    public Member starting_membership(LocalDate starting_membership) {
        this.starting_membership = starting_membership;
        return this;
    }

    public void setStarting_membership(LocalDate starting_membership) {
        this.starting_membership = starting_membership;
    }

    public LocalDate getEnding_membership() {
        return ending_membership;
    }

    public Member ending_membership(LocalDate ending_membership) {
        this.ending_membership = ending_membership;
        return this;
    }

    public void setEnding_membership(LocalDate ending_membership) {
        this.ending_membership = ending_membership;
    }

    public String getEmail() {
        return email;
    }

    public Member email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Member phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Club getClub() {
        return club;
    }

    public Member club(Club club) {
        this.club = club;
        return this;
    }

    public void setClub(Club club) {
        this.club = club;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", status='" + getStatus() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", position='" + getPosition() + "'" +
            ", committee='" + getCommittee() + "'" +
            ", professional_area='" + getProfessional_area() + "'" +
            ", starting_membership='" + getStarting_membership() + "'" +
            ", ending_membership='" + getEnding_membership() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
