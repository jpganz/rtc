package com.rotaract.project.web.rest;

import com.rotaract.project.RtcprojectApp;

import com.rotaract.project.domain.Project;
import com.rotaract.project.domain.Club;
import com.rotaract.project.repository.ProjectRepository;
import com.rotaract.project.service.ProjectService;
import com.rotaract.project.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.rotaract.project.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rotaract.project.domain.enumeration.AreaOfInteresEnum;
import com.rotaract.project.domain.enumeration.OrganizerCommitteeEnum;
import com.rotaract.project.domain.enumeration.ProjectStatusEnum;
/**
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtcprojectApp.class)
public class ProjectResourceIntTest {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final AreaOfInteresEnum DEFAULT_AREA_OF_INTERES = AreaOfInteresEnum.FOOD;
    private static final AreaOfInteresEnum UPDATED_AREA_OF_INTERES = AreaOfInteresEnum.WATER;

    private static final OrganizerCommitteeEnum DEFAULT_ORGANIZER_COMMITTEE = OrganizerCommitteeEnum.CLUB_SERVICE;
    private static final OrganizerCommitteeEnum UPDATED_ORGANIZER_COMMITTEE = OrganizerCommitteeEnum.YOUTH;

    private static final String DEFAULT_COORDINATOR = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATOR = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MAIN_OBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_OBJECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_COMMUNITY = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_COMMUNITY = "BBBBBBBBBB";

    private static final Long DEFAULT_DIRECT_BENEFICIARIES = 0L;
    private static final Long UPDATED_DIRECT_BENEFICIARIES = 1L;

    private static final Long DEFAULT_INDIRECT_BENEFICIARIES = 0L;
    private static final Long UPDATED_INDIRECT_BENEFICIARIES = 1L;

    private static final String DEFAULT_SHORT_TERM_BENEFITS = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_TERM_BENEFITS = "BBBBBBBBBB";

    private static final String DEFAULT_MID_TERM_BENEFITS = "AAAAAAAAAA";
    private static final String UPDATED_MID_TERM_BENEFITS = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_TERM_BENEFITS = "AAAAAAAAAA";
    private static final String UPDATED_LONG_TERM_BENEFITS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MEMBER_CONTRIBUTIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEMBER_CONTRIBUTIONS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FUNDRAISING = new BigDecimal(1);
    private static final BigDecimal UPDATED_FUNDRAISING = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DONATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_DONATIONS = new BigDecimal(2);

    private static final String DEFAULT_DONATIONS_IN_KIND = "AAAAAAAAAA";
    private static final String UPDATED_DONATIONS_IN_KIND = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_MEDIA_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_MEDIA_REFERENCE = "BBBBBBBBBB";

    private static final ProjectStatusEnum DEFAULT_PROJECT_STATUS = ProjectStatusEnum.SUBMITTED;
    private static final ProjectStatusEnum UPDATED_PROJECT_STATUS = ProjectStatusEnum.PENDING;

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectMockMvc;

    private Project project;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectResource projectResource = new ProjectResource(projectService);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .project_name(DEFAULT_PROJECT_NAME)
            .area_of_interes(DEFAULT_AREA_OF_INTERES)
            .organizer_committee(DEFAULT_ORGANIZER_COMMITTEE)
            .coordinator(DEFAULT_COORDINATOR)
            .location(DEFAULT_LOCATION)
            .date(DEFAULT_DATE)
            .main_objective(DEFAULT_MAIN_OBJECTIVE)
            .target_community(DEFAULT_TARGET_COMMUNITY)
            .direct_beneficiaries(DEFAULT_DIRECT_BENEFICIARIES)
            .indirect_beneficiaries(DEFAULT_INDIRECT_BENEFICIARIES)
            .short_term_benefits(DEFAULT_SHORT_TERM_BENEFITS)
            .mid_term_benefits(DEFAULT_MID_TERM_BENEFITS)
            .long_term_benefits(DEFAULT_LONG_TERM_BENEFITS)
            .member_contributions(DEFAULT_MEMBER_CONTRIBUTIONS)
            .fundraising(DEFAULT_FUNDRAISING)
            .donations(DEFAULT_DONATIONS)
            .donations_in_kind(DEFAULT_DONATIONS_IN_KIND)
            .activity_description(DEFAULT_ACTIVITY_DESCRIPTION)
            .social_media_reference(DEFAULT_SOCIAL_MEDIA_REFERENCE)
            .project_status(DEFAULT_PROJECT_STATUS);
        // Add required entity
        Club club = ClubResourceIntTest.createEntity(em);
        em.persist(club);
        em.flush();
        project.setClub(club);
        return project;
    }

    @Before
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProject_name()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProject.getArea_of_interes()).isEqualTo(DEFAULT_AREA_OF_INTERES);
        assertThat(testProject.getOrganizer_committee()).isEqualTo(DEFAULT_ORGANIZER_COMMITTEE);
        assertThat(testProject.getCoordinator()).isEqualTo(DEFAULT_COORDINATOR);
        assertThat(testProject.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProject.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProject.getMain_objective()).isEqualTo(DEFAULT_MAIN_OBJECTIVE);
        assertThat(testProject.getTarget_community()).isEqualTo(DEFAULT_TARGET_COMMUNITY);
        assertThat(testProject.getDirect_beneficiaries()).isEqualTo(DEFAULT_DIRECT_BENEFICIARIES);
        assertThat(testProject.getIndirect_beneficiaries()).isEqualTo(DEFAULT_INDIRECT_BENEFICIARIES);
        assertThat(testProject.getShort_term_benefits()).isEqualTo(DEFAULT_SHORT_TERM_BENEFITS);
        assertThat(testProject.getMid_term_benefits()).isEqualTo(DEFAULT_MID_TERM_BENEFITS);
        assertThat(testProject.getLong_term_benefits()).isEqualTo(DEFAULT_LONG_TERM_BENEFITS);
        assertThat(testProject.getMember_contributions()).isEqualTo(DEFAULT_MEMBER_CONTRIBUTIONS);
        assertThat(testProject.getFundraising()).isEqualTo(DEFAULT_FUNDRAISING);
        assertThat(testProject.getDonations()).isEqualTo(DEFAULT_DONATIONS);
        assertThat(testProject.getDonations_in_kind()).isEqualTo(DEFAULT_DONATIONS_IN_KIND);
        assertThat(testProject.getActivity_description()).isEqualTo(DEFAULT_ACTIVITY_DESCRIPTION);
        assertThat(testProject.getSocial_media_reference()).isEqualTo(DEFAULT_SOCIAL_MEDIA_REFERENCE);
        assertThat(testProject.getProject_status()).isEqualTo(DEFAULT_PROJECT_STATUS);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProject_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setProject_name(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArea_of_interesIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setArea_of_interes(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganizer_committeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setOrganizer_committee(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCoordinatorIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setCoordinator(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setLocation(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setDate(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMain_objectiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setMain_objective(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTarget_communityIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setTarget_community(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDirect_beneficiariesIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setDirect_beneficiaries(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShort_term_benefitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setShort_term_benefits(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivity_descriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setActivity_description(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProject_statusIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setProject_status(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].project_name").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].area_of_interes").value(hasItem(DEFAULT_AREA_OF_INTERES.toString())))
            .andExpect(jsonPath("$.[*].organizer_committee").value(hasItem(DEFAULT_ORGANIZER_COMMITTEE.toString())))
            .andExpect(jsonPath("$.[*].coordinator").value(hasItem(DEFAULT_COORDINATOR.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].main_objective").value(hasItem(DEFAULT_MAIN_OBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].target_community").value(hasItem(DEFAULT_TARGET_COMMUNITY.toString())))
            .andExpect(jsonPath("$.[*].direct_beneficiaries").value(hasItem(DEFAULT_DIRECT_BENEFICIARIES.intValue())))
            .andExpect(jsonPath("$.[*].indirect_beneficiaries").value(hasItem(DEFAULT_INDIRECT_BENEFICIARIES.intValue())))
            .andExpect(jsonPath("$.[*].short_term_benefits").value(hasItem(DEFAULT_SHORT_TERM_BENEFITS.toString())))
            .andExpect(jsonPath("$.[*].mid_term_benefits").value(hasItem(DEFAULT_MID_TERM_BENEFITS.toString())))
            .andExpect(jsonPath("$.[*].long_term_benefits").value(hasItem(DEFAULT_LONG_TERM_BENEFITS.toString())))
            .andExpect(jsonPath("$.[*].member_contributions").value(hasItem(DEFAULT_MEMBER_CONTRIBUTIONS.intValue())))
            .andExpect(jsonPath("$.[*].fundraising").value(hasItem(DEFAULT_FUNDRAISING.intValue())))
            .andExpect(jsonPath("$.[*].donations").value(hasItem(DEFAULT_DONATIONS.intValue())))
            .andExpect(jsonPath("$.[*].donations_in_kind").value(hasItem(DEFAULT_DONATIONS_IN_KIND.toString())))
            .andExpect(jsonPath("$.[*].activity_description").value(hasItem(DEFAULT_ACTIVITY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].social_media_reference").value(hasItem(DEFAULT_SOCIAL_MEDIA_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].project_status").value(hasItem(DEFAULT_PROJECT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.project_name").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.area_of_interes").value(DEFAULT_AREA_OF_INTERES.toString()))
            .andExpect(jsonPath("$.organizer_committee").value(DEFAULT_ORGANIZER_COMMITTEE.toString()))
            .andExpect(jsonPath("$.coordinator").value(DEFAULT_COORDINATOR.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.main_objective").value(DEFAULT_MAIN_OBJECTIVE.toString()))
            .andExpect(jsonPath("$.target_community").value(DEFAULT_TARGET_COMMUNITY.toString()))
            .andExpect(jsonPath("$.direct_beneficiaries").value(DEFAULT_DIRECT_BENEFICIARIES.intValue()))
            .andExpect(jsonPath("$.indirect_beneficiaries").value(DEFAULT_INDIRECT_BENEFICIARIES.intValue()))
            .andExpect(jsonPath("$.short_term_benefits").value(DEFAULT_SHORT_TERM_BENEFITS.toString()))
            .andExpect(jsonPath("$.mid_term_benefits").value(DEFAULT_MID_TERM_BENEFITS.toString()))
            .andExpect(jsonPath("$.long_term_benefits").value(DEFAULT_LONG_TERM_BENEFITS.toString()))
            .andExpect(jsonPath("$.member_contributions").value(DEFAULT_MEMBER_CONTRIBUTIONS.intValue()))
            .andExpect(jsonPath("$.fundraising").value(DEFAULT_FUNDRAISING.intValue()))
            .andExpect(jsonPath("$.donations").value(DEFAULT_DONATIONS.intValue()))
            .andExpect(jsonPath("$.donations_in_kind").value(DEFAULT_DONATIONS_IN_KIND.toString()))
            .andExpect(jsonPath("$.activity_description").value(DEFAULT_ACTIVITY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.social_media_reference").value(DEFAULT_SOCIAL_MEDIA_REFERENCE.toString()))
            .andExpect(jsonPath("$.project_status").value(DEFAULT_PROJECT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectService.save(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .project_name(UPDATED_PROJECT_NAME)
            .area_of_interes(UPDATED_AREA_OF_INTERES)
            .organizer_committee(UPDATED_ORGANIZER_COMMITTEE)
            .coordinator(UPDATED_COORDINATOR)
            .location(UPDATED_LOCATION)
            .date(UPDATED_DATE)
            .main_objective(UPDATED_MAIN_OBJECTIVE)
            .target_community(UPDATED_TARGET_COMMUNITY)
            .direct_beneficiaries(UPDATED_DIRECT_BENEFICIARIES)
            .indirect_beneficiaries(UPDATED_INDIRECT_BENEFICIARIES)
            .short_term_benefits(UPDATED_SHORT_TERM_BENEFITS)
            .mid_term_benefits(UPDATED_MID_TERM_BENEFITS)
            .long_term_benefits(UPDATED_LONG_TERM_BENEFITS)
            .member_contributions(UPDATED_MEMBER_CONTRIBUTIONS)
            .fundraising(UPDATED_FUNDRAISING)
            .donations(UPDATED_DONATIONS)
            .donations_in_kind(UPDATED_DONATIONS_IN_KIND)
            .activity_description(UPDATED_ACTIVITY_DESCRIPTION)
            .social_media_reference(UPDATED_SOCIAL_MEDIA_REFERENCE)
            .project_status(UPDATED_PROJECT_STATUS);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProject_name()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProject.getArea_of_interes()).isEqualTo(UPDATED_AREA_OF_INTERES);
        assertThat(testProject.getOrganizer_committee()).isEqualTo(UPDATED_ORGANIZER_COMMITTEE);
        assertThat(testProject.getCoordinator()).isEqualTo(UPDATED_COORDINATOR);
        assertThat(testProject.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProject.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProject.getMain_objective()).isEqualTo(UPDATED_MAIN_OBJECTIVE);
        assertThat(testProject.getTarget_community()).isEqualTo(UPDATED_TARGET_COMMUNITY);
        assertThat(testProject.getDirect_beneficiaries()).isEqualTo(UPDATED_DIRECT_BENEFICIARIES);
        assertThat(testProject.getIndirect_beneficiaries()).isEqualTo(UPDATED_INDIRECT_BENEFICIARIES);
        assertThat(testProject.getShort_term_benefits()).isEqualTo(UPDATED_SHORT_TERM_BENEFITS);
        assertThat(testProject.getMid_term_benefits()).isEqualTo(UPDATED_MID_TERM_BENEFITS);
        assertThat(testProject.getLong_term_benefits()).isEqualTo(UPDATED_LONG_TERM_BENEFITS);
        assertThat(testProject.getMember_contributions()).isEqualTo(UPDATED_MEMBER_CONTRIBUTIONS);
        assertThat(testProject.getFundraising()).isEqualTo(UPDATED_FUNDRAISING);
        assertThat(testProject.getDonations()).isEqualTo(UPDATED_DONATIONS);
        assertThat(testProject.getDonations_in_kind()).isEqualTo(UPDATED_DONATIONS_IN_KIND);
        assertThat(testProject.getActivity_description()).isEqualTo(UPDATED_ACTIVITY_DESCRIPTION);
        assertThat(testProject.getSocial_media_reference()).isEqualTo(UPDATED_SOCIAL_MEDIA_REFERENCE);
        assertThat(testProject.getProject_status()).isEqualTo(UPDATED_PROJECT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Create the Project

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectService.save(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Get the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = new Project();
        project1.setId(1L);
        Project project2 = new Project();
        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);
        project2.setId(2L);
        assertThat(project1).isNotEqualTo(project2);
        project1.setId(null);
        assertThat(project1).isNotEqualTo(project2);
    }
}
