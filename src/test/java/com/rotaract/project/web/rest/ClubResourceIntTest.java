package com.rotaract.project.web.rest;

import com.rotaract.project.RtcprojectApp;

import com.rotaract.project.domain.Club;
import com.rotaract.project.repository.ClubRepository;
import com.rotaract.project.service.ClubService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.rotaract.project.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rotaract.project.domain.enumeration.CountryEnum;
import com.rotaract.project.domain.enumeration.CorEnum;
import com.rotaract.project.domain.enumeration.WeekdaysEnum;
import com.rotaract.project.domain.enumeration.ClubStatusEnum;
/**
 * Test class for the ClubResource REST controller.
 *
 * @see ClubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtcprojectApp.class)
public class ClubResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CountryEnum DEFAULT_COUNTRY = CountryEnum.GUATEMALA;
    private static final CountryEnum UPDATED_COUNTRY = CountryEnum.BELIZE;

    private static final CorEnum DEFAULT_COR = CorEnum.GUATEMALA;
    private static final CorEnum UPDATED_COR = CorEnum.GUATEMALA;

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_ROTARACT_ID = 0L;
    private static final Long UPDATED_ROTARACT_ID = 1L;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CERTIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CERTIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final WeekdaysEnum DEFAULT_MEETING_DAY = WeekdaysEnum.SUNDAY;
    private static final WeekdaysEnum UPDATED_MEETING_DAY = WeekdaysEnum.MONDAY;

    private static final String DEFAULT_MEETING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_MEETING_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_Y = "AAAAAAAAAA";
    private static final String UPDATED_Y = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ROTARY_CLUB = "AAAAAAAAAA";
    private static final String UPDATED_ROTARY_CLUB = "BBBBBBBBBB";

    private static final ClubStatusEnum DEFAULT_STATUS = ClubStatusEnum.ACTIVE;
    private static final ClubStatusEnum UPDATED_STATUS = ClubStatusEnum.INACTIVE;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private ClubService clubService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClubMockMvc;

    private Club club;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClubResource clubResource = new ClubResource(clubService);
        this.restClubMockMvc = MockMvcBuilders.standaloneSetup(clubResource)
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
    public static Club createEntity(EntityManager em) {
        Club club = new Club()
            .name(DEFAULT_NAME)
            .country(DEFAULT_COUNTRY)
            .cor(DEFAULT_COR)
            .state(DEFAULT_STATE)
            .city(DEFAULT_CITY)
            .address(DEFAULT_ADDRESS)
            .rotaract_id(DEFAULT_ROTARACT_ID)
            .creation_date(DEFAULT_CREATION_DATE)
            .certification_date(DEFAULT_CERTIFICATION_DATE)
            .meeting_day(DEFAULT_MEETING_DAY)
            .meeting_time(DEFAULT_MEETING_TIME)
            .y(DEFAULT_Y)
            .facebook_url(DEFAULT_FACEBOOK_URL)
            .instagram_url(DEFAULT_INSTAGRAM_URL)
            .twitter_url(DEFAULT_TWITTER_URL)
            .rotary_club(DEFAULT_ROTARY_CLUB)
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS);
        return club;
    }

    @Before
    public void initTest() {
        club = createEntity(em);
    }

    @Test
    @Transactional
    public void createClub() throws Exception {
        int databaseSizeBeforeCreate = clubRepository.findAll().size();

        // Create the Club
        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isCreated());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeCreate + 1);
        Club testClub = clubList.get(clubList.size() - 1);
        assertThat(testClub.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClub.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testClub.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testClub.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testClub.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClub.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClub.getRotaract_id()).isEqualTo(DEFAULT_ROTARACT_ID);
        assertThat(testClub.getCreation_date()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testClub.getCertification_date()).isEqualTo(DEFAULT_CERTIFICATION_DATE);
        assertThat(testClub.getMeeting_day()).isEqualTo(DEFAULT_MEETING_DAY);
        assertThat(testClub.getMeeting_time()).isEqualTo(DEFAULT_MEETING_TIME);
        assertThat(testClub.getY()).isEqualTo(DEFAULT_Y);
        assertThat(testClub.getFacebook_url()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testClub.getInstagram_url()).isEqualTo(DEFAULT_INSTAGRAM_URL);
        assertThat(testClub.getTwitter_url()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testClub.getRotary_club()).isEqualTo(DEFAULT_ROTARY_CLUB);
        assertThat(testClub.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClub.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createClubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clubRepository.findAll().size();

        // Create the Club with an existing ID
        club.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setName(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setCountry(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setCor(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setState(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setCity(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setAddress(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRotaract_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setRotaract_id(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreation_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setCreation_date(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertification_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setCertification_date(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeeting_dayIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setMeeting_day(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeeting_timeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setMeeting_time(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRotary_clubIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setRotary_club(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setStatus(null);

        // Create the Club, which fails.

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClubs() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList
        restClubMockMvc.perform(get("/api/clubs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(club.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].rotaract_id").value(hasItem(DEFAULT_ROTARACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].certification_date").value(hasItem(DEFAULT_CERTIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].meeting_day").value(hasItem(DEFAULT_MEETING_DAY.toString())))
            .andExpect(jsonPath("$.[*].meeting_time").value(hasItem(DEFAULT_MEETING_TIME.toString())))
            .andExpect(jsonPath("$.[*].y").value(hasItem(DEFAULT_Y.toString())))
            .andExpect(jsonPath("$.[*].facebook_url").value(hasItem(DEFAULT_FACEBOOK_URL.toString())))
            .andExpect(jsonPath("$.[*].instagram_url").value(hasItem(DEFAULT_INSTAGRAM_URL.toString())))
            .andExpect(jsonPath("$.[*].twitter_url").value(hasItem(DEFAULT_TWITTER_URL.toString())))
            .andExpect(jsonPath("$.[*].rotary_club").value(hasItem(DEFAULT_ROTARY_CLUB.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }
    
    @Test
    @Transactional
    public void getClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", club.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(club.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.rotaract_id").value(DEFAULT_ROTARACT_ID.intValue()))
            .andExpect(jsonPath("$.creation_date").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.certification_date").value(DEFAULT_CERTIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.meeting_day").value(DEFAULT_MEETING_DAY.toString()))
            .andExpect(jsonPath("$.meeting_time").value(DEFAULT_MEETING_TIME.toString()))
            .andExpect(jsonPath("$.y").value(DEFAULT_Y.toString()))
            .andExpect(jsonPath("$.facebook_url").value(DEFAULT_FACEBOOK_URL.toString()))
            .andExpect(jsonPath("$.instagram_url").value(DEFAULT_INSTAGRAM_URL.toString()))
            .andExpect(jsonPath("$.twitter_url").value(DEFAULT_TWITTER_URL.toString()))
            .andExpect(jsonPath("$.rotary_club").value(DEFAULT_ROTARY_CLUB.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClub() throws Exception {
        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClub() throws Exception {
        // Initialize the database
        clubService.save(club);

        int databaseSizeBeforeUpdate = clubRepository.findAll().size();

        // Update the club
        Club updatedClub = clubRepository.findById(club.getId()).get();
        // Disconnect from session so that the updates on updatedClub are not directly saved in db
        em.detach(updatedClub);
        updatedClub
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .cor(UPDATED_COR)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .address(UPDATED_ADDRESS)
            .rotaract_id(UPDATED_ROTARACT_ID)
            .creation_date(UPDATED_CREATION_DATE)
            .certification_date(UPDATED_CERTIFICATION_DATE)
            .meeting_day(UPDATED_MEETING_DAY)
            .meeting_time(UPDATED_MEETING_TIME)
            .y(UPDATED_Y)
            .facebook_url(UPDATED_FACEBOOK_URL)
            .instagram_url(UPDATED_INSTAGRAM_URL)
            .twitter_url(UPDATED_TWITTER_URL)
            .rotary_club(UPDATED_ROTARY_CLUB)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);

        restClubMockMvc.perform(put("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClub)))
            .andExpect(status().isOk());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeUpdate);
        Club testClub = clubList.get(clubList.size() - 1);
        assertThat(testClub.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClub.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClub.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testClub.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testClub.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClub.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClub.getRotaract_id()).isEqualTo(UPDATED_ROTARACT_ID);
        assertThat(testClub.getCreation_date()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testClub.getCertification_date()).isEqualTo(UPDATED_CERTIFICATION_DATE);
        assertThat(testClub.getMeeting_day()).isEqualTo(UPDATED_MEETING_DAY);
        assertThat(testClub.getMeeting_time()).isEqualTo(UPDATED_MEETING_TIME);
        assertThat(testClub.getY()).isEqualTo(UPDATED_Y);
        assertThat(testClub.getFacebook_url()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testClub.getInstagram_url()).isEqualTo(UPDATED_INSTAGRAM_URL);
        assertThat(testClub.getTwitter_url()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testClub.getRotary_club()).isEqualTo(UPDATED_ROTARY_CLUB);
        assertThat(testClub.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClub.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingClub() throws Exception {
        int databaseSizeBeforeUpdate = clubRepository.findAll().size();

        // Create the Club

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClubMockMvc.perform(put("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(club)))
            .andExpect(status().isBadRequest());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClub() throws Exception {
        // Initialize the database
        clubService.save(club);

        int databaseSizeBeforeDelete = clubRepository.findAll().size();

        // Get the club
        restClubMockMvc.perform(delete("/api/clubs/{id}", club.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Club.class);
        Club club1 = new Club();
        club1.setId(1L);
        Club club2 = new Club();
        club2.setId(club1.getId());
        assertThat(club1).isEqualTo(club2);
        club2.setId(2L);
        assertThat(club1).isNotEqualTo(club2);
        club1.setId(null);
        assertThat(club1).isNotEqualTo(club2);
    }
}
