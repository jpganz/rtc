package com.rotaract.project.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.rotaract.project.domain.enumeration.CountryEnum;

import com.rotaract.project.domain.enumeration.CorEnum;

import com.rotaract.project.domain.enumeration.WeekdaysEnum;

import com.rotaract.project.domain.enumeration.ClubStatusEnum;

/**
 * A Club.
 */
@Entity
@Table(name = "club")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private CountryEnum country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cor", nullable = false)
    private CorEnum cor;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Min(value = 0L)
    @Column(name = "rotaract_id", nullable = false)
    private Long rotaract_id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creation_date;

    @NotNull
    @Column(name = "certification_date", nullable = false)
    private LocalDate certification_date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_day", nullable = false)
    private WeekdaysEnum meeting_day;

    @NotNull
    @Column(name = "meeting_time", nullable = false)
    private String meeting_time;

    @Column(name = "facebook_url")
    private String facebook_url;

    @Column(name = "instagram_url")
    private String instagram_url;

    @Column(name = "twitter_url")
    private String twitter_url;

    @NotNull
    @Column(name = "rotary_club", nullable = false)
    private String rotary_club;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ClubStatusEnum status;

    @Column(name = "comments")
    private String comments;

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

    public Club name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public Club country(CountryEnum country) {
        this.country = country;
        return this;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public CorEnum getCor() {
        return cor;
    }

    public Club cor(CorEnum cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(CorEnum cor) {
        this.cor = cor;
    }

    public String getState() {
        return state;
    }

    public Club state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public Club city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public Club address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getRotaract_id() {
        return rotaract_id;
    }

    public Club rotaract_id(Long rotaract_id) {
        this.rotaract_id = rotaract_id;
        return this;
    }

    public void setRotaract_id(Long rotaract_id) {
        this.rotaract_id = rotaract_id;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public Club creation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
        return this;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDate getCertification_date() {
        return certification_date;
    }

    public Club certification_date(LocalDate certification_date) {
        this.certification_date = certification_date;
        return this;
    }

    public void setCertification_date(LocalDate certification_date) {
        this.certification_date = certification_date;
    }

    public WeekdaysEnum getMeeting_day() {
        return meeting_day;
    }

    public Club meeting_day(WeekdaysEnum meeting_day) {
        this.meeting_day = meeting_day;
        return this;
    }

    public void setMeeting_day(WeekdaysEnum meeting_day) {
        this.meeting_day = meeting_day;
    }

    public String getMeeting_time() {
        return meeting_time;
    }

    public Club meeting_time(String meeting_time) {
        this.meeting_time = meeting_time;
        return this;
    }

    public void setMeeting_time(String meeting_time) {
        this.meeting_time = meeting_time;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public Club facebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
        return this;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public String getInstagram_url() {
        return instagram_url;
    }

    public Club instagram_url(String instagram_url) {
        this.instagram_url = instagram_url;
        return this;
    }

    public void setInstagram_url(String instagram_url) {
        this.instagram_url = instagram_url;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public Club twitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
        return this;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }

    public String getRotary_club() {
        return rotary_club;
    }

    public Club rotary_club(String rotary_club) {
        this.rotary_club = rotary_club;
        return this;
    }

    public void setRotary_club(String rotary_club) {
        this.rotary_club = rotary_club;
    }

    public ClubStatusEnum getStatus() {
        return status;
    }

    public Club status(ClubStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(ClubStatusEnum status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public Club comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        Club club = (Club) o;
        if (club.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), club.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Club{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", cor='" + getCor() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", address='" + getAddress() + "'" +
            ", rotaract_id=" + getRotaract_id() +
            ", creation_date='" + getCreation_date() + "'" +
            ", certification_date='" + getCertification_date() + "'" +
            ", meeting_day='" + getMeeting_day() + "'" +
            ", meeting_time='" + getMeeting_time() + "'" +
            ", facebook_url='" + getFacebook_url() + "'" +
            ", instagram_url='" + getInstagram_url() + "'" +
            ", twitter_url='" + getTwitter_url() + "'" +
            ", rotary_club='" + getRotary_club() + "'" +
            ", status='" + getStatus() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
