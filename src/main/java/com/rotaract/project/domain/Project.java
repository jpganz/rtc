package com.rotaract.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.rotaract.project.domain.enumeration.AreaOfInteresEnum;

import com.rotaract.project.domain.enumeration.OrganizerCommitteeEnum;

import com.rotaract.project.domain.enumeration.ProjectStatusEnum;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String project_name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area_of_interes", nullable = false)
    private AreaOfInteresEnum area_of_interes;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "organizer_committee", nullable = false)
    private OrganizerCommitteeEnum organizer_committee;

    @NotNull
    @Column(name = "coordinator", nullable = false)
    private String coordinator;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "main_objective", nullable = false)
    private String main_objective;

    @NotNull
    @Column(name = "target_community", nullable = false)
    private String target_community;

    @NotNull
    @Min(value = 0L)
    @Column(name = "direct_beneficiaries", nullable = false)
    private Long direct_beneficiaries;

    @Min(value = 0L)
    @Column(name = "indirect_beneficiaries")
    private Long indirect_beneficiaries;

    @NotNull
    @Column(name = "short_term_benefits", nullable = false)
    private String short_term_benefits;

    @Column(name = "mid_term_benefits")
    private String mid_term_benefits;

    @Column(name = "long_term_benefits")
    private String long_term_benefits;

    @Column(name = "member_contributions", precision = 10, scale = 2)
    private BigDecimal member_contributions;

    @Column(name = "fundraising", precision = 10, scale = 2)
    private BigDecimal fundraising;

    @Column(name = "donations", precision = 10, scale = 2)
    private BigDecimal donations;

    @Column(name = "donations_in_kind")
    private String donations_in_kind;

    @NotNull
    @Column(name = "activity_description", nullable = false)
    private String activity_description;

    @Column(name = "social_media_reference")
    private String social_media_reference;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", nullable = false)
    private ProjectStatusEnum project_status;

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

    public String getProject_name() {
        return project_name;
    }

    public Project project_name(String project_name) {
        this.project_name = project_name;
        return this;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public AreaOfInteresEnum getArea_of_interes() {
        return area_of_interes;
    }

    public Project area_of_interes(AreaOfInteresEnum area_of_interes) {
        this.area_of_interes = area_of_interes;
        return this;
    }

    public void setArea_of_interes(AreaOfInteresEnum area_of_interes) {
        this.area_of_interes = area_of_interes;
    }

    public OrganizerCommitteeEnum getOrganizer_committee() {
        return organizer_committee;
    }

    public Project organizer_committee(OrganizerCommitteeEnum organizer_committee) {
        this.organizer_committee = organizer_committee;
        return this;
    }

    public void setOrganizer_committee(OrganizerCommitteeEnum organizer_committee) {
        this.organizer_committee = organizer_committee;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public Project coordinator(String coordinator) {
        this.coordinator = coordinator;
        return this;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public String getLocation() {
        return location;
    }

    public Project location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public Project date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMain_objective() {
        return main_objective;
    }

    public Project main_objective(String main_objective) {
        this.main_objective = main_objective;
        return this;
    }

    public void setMain_objective(String main_objective) {
        this.main_objective = main_objective;
    }

    public String getTarget_community() {
        return target_community;
    }

    public Project target_community(String target_community) {
        this.target_community = target_community;
        return this;
    }

    public void setTarget_community(String target_community) {
        this.target_community = target_community;
    }

    public Long getDirect_beneficiaries() {
        return direct_beneficiaries;
    }

    public Project direct_beneficiaries(Long direct_beneficiaries) {
        this.direct_beneficiaries = direct_beneficiaries;
        return this;
    }

    public void setDirect_beneficiaries(Long direct_beneficiaries) {
        this.direct_beneficiaries = direct_beneficiaries;
    }

    public Long getIndirect_beneficiaries() {
        return indirect_beneficiaries;
    }

    public Project indirect_beneficiaries(Long indirect_beneficiaries) {
        this.indirect_beneficiaries = indirect_beneficiaries;
        return this;
    }

    public void setIndirect_beneficiaries(Long indirect_beneficiaries) {
        this.indirect_beneficiaries = indirect_beneficiaries;
    }

    public String getShort_term_benefits() {
        return short_term_benefits;
    }

    public Project short_term_benefits(String short_term_benefits) {
        this.short_term_benefits = short_term_benefits;
        return this;
    }

    public void setShort_term_benefits(String short_term_benefits) {
        this.short_term_benefits = short_term_benefits;
    }

    public String getMid_term_benefits() {
        return mid_term_benefits;
    }

    public Project mid_term_benefits(String mid_term_benefits) {
        this.mid_term_benefits = mid_term_benefits;
        return this;
    }

    public void setMid_term_benefits(String mid_term_benefits) {
        this.mid_term_benefits = mid_term_benefits;
    }

    public String getLong_term_benefits() {
        return long_term_benefits;
    }

    public Project long_term_benefits(String long_term_benefits) {
        this.long_term_benefits = long_term_benefits;
        return this;
    }

    public void setLong_term_benefits(String long_term_benefits) {
        this.long_term_benefits = long_term_benefits;
    }

    public BigDecimal getMember_contributions() {
        return member_contributions;
    }

    public Project member_contributions(BigDecimal member_contributions) {
        this.member_contributions = member_contributions;
        return this;
    }

    public void setMember_contributions(BigDecimal member_contributions) {
        this.member_contributions = member_contributions;
    }

    public BigDecimal getFundraising() {
        return fundraising;
    }

    public Project fundraising(BigDecimal fundraising) {
        this.fundraising = fundraising;
        return this;
    }

    public void setFundraising(BigDecimal fundraising) {
        this.fundraising = fundraising;
    }

    public BigDecimal getDonations() {
        return donations;
    }

    public Project donations(BigDecimal donations) {
        this.donations = donations;
        return this;
    }

    public void setDonations(BigDecimal donations) {
        this.donations = donations;
    }

    public String getDonations_in_kind() {
        return donations_in_kind;
    }

    public Project donations_in_kind(String donations_in_kind) {
        this.donations_in_kind = donations_in_kind;
        return this;
    }

    public void setDonations_in_kind(String donations_in_kind) {
        this.donations_in_kind = donations_in_kind;
    }

    public String getActivity_description() {
        return activity_description;
    }

    public Project activity_description(String activity_description) {
        this.activity_description = activity_description;
        return this;
    }

    public void setActivity_description(String activity_description) {
        this.activity_description = activity_description;
    }

    public String getSocial_media_reference() {
        return social_media_reference;
    }

    public Project social_media_reference(String social_media_reference) {
        this.social_media_reference = social_media_reference;
        return this;
    }

    public void setSocial_media_reference(String social_media_reference) {
        this.social_media_reference = social_media_reference;
    }

    public ProjectStatusEnum getProject_status() {
        return project_status;
    }

    public Project project_status(ProjectStatusEnum project_status) {
        this.project_status = project_status;
        return this;
    }

    public void setProject_status(ProjectStatusEnum project_status) {
        this.project_status = project_status;
    }

    public Club getClub() {
        return club;
    }

    public Project club(Club club) {
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", project_name='" + getProject_name() + "'" +
            ", area_of_interes='" + getArea_of_interes() + "'" +
            ", organizer_committee='" + getOrganizer_committee() + "'" +
            ", coordinator='" + getCoordinator() + "'" +
            ", location='" + getLocation() + "'" +
            ", date='" + getDate() + "'" +
            ", main_objective='" + getMain_objective() + "'" +
            ", target_community='" + getTarget_community() + "'" +
            ", direct_beneficiaries=" + getDirect_beneficiaries() +
            ", indirect_beneficiaries=" + getIndirect_beneficiaries() +
            ", short_term_benefits='" + getShort_term_benefits() + "'" +
            ", mid_term_benefits='" + getMid_term_benefits() + "'" +
            ", long_term_benefits='" + getLong_term_benefits() + "'" +
            ", member_contributions=" + getMember_contributions() +
            ", fundraising=" + getFundraising() +
            ", donations=" + getDonations() +
            ", donations_in_kind='" + getDonations_in_kind() + "'" +
            ", activity_description='" + getActivity_description() + "'" +
            ", social_media_reference='" + getSocial_media_reference() + "'" +
            ", project_status='" + getProject_status() + "'" +
            "}";
    }
}
