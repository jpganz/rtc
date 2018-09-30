package com.rotaract.project.web.rest;

import com.rotaract.project.RtcprojectApp;

import com.rotaract.project.domain.Member;
import com.rotaract.project.domain.Club;
import com.rotaract.project.repository.MemberRepository;
import com.rotaract.project.service.MemberService;
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

import com.rotaract.project.domain.enumeration.MemberStatusEnum;
import com.rotaract.project.domain.enumeration.GenderEnum;
import com.rotaract.project.domain.enumeration.PositionEnum;
import com.rotaract.project.domain.enumeration.CommitteeEnum;
import com.rotaract.project.domain.enumeration.ProfessionalAreaEnum;
/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtcprojectApp.class)
public class MemberResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final MemberStatusEnum DEFAULT_STATUS = MemberStatusEnum.ACTIVE;
    private static final MemberStatusEnum UPDATED_STATUS = MemberStatusEnum.ABSENT_ON_LEAVE;

    private static final GenderEnum DEFAULT_GENDER = GenderEnum.MALE;
    private static final GenderEnum UPDATED_GENDER = GenderEnum.FEMALE;

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final PositionEnum DEFAULT_POSITION = PositionEnum.PRESIDENT;
    private static final PositionEnum UPDATED_POSITION = PositionEnum.VICEPRESIDENT;

    private static final CommitteeEnum DEFAULT_COMMITTEE = CommitteeEnum.INTERNATIONAL_SERVICE;
    private static final CommitteeEnum UPDATED_COMMITTEE = CommitteeEnum.FINANCE;

    private static final ProfessionalAreaEnum DEFAULT_PROFESSIONAL_AREA = ProfessionalAreaEnum.LEGAL;
    private static final ProfessionalAreaEnum UPDATED_PROFESSIONAL_AREA = ProfessionalAreaEnum.FINANCE;

    private static final LocalDate DEFAULT_STARTING_MEMBERSHIP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTING_MEMBERSHIP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDING_MEMBERSHIP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDING_MEMBERSHIP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberResource memberResource = new MemberResource(memberService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
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
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .name(DEFAULT_NAME)
            .last_name(DEFAULT_LAST_NAME)
            .status(DEFAULT_STATUS)
            .gender(DEFAULT_GENDER)
            .birthdate(DEFAULT_BIRTHDATE)
            .position(DEFAULT_POSITION)
            .committee(DEFAULT_COMMITTEE)
            .professional_area(DEFAULT_PROFESSIONAL_AREA)
            .starting_membership(DEFAULT_STARTING_MEMBERSHIP)
            .ending_membership(DEFAULT_ENDING_MEMBERSHIP)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        // Add required entity
        Club club = ClubResourceIntTest.createEntity(em);
        em.persist(club);
        em.flush();
        member.setClub(club);
        return member;
    }

    @Before
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMember.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMember.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testMember.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testMember.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testMember.getCommittee()).isEqualTo(DEFAULT_COMMITTEE);
        assertThat(testMember.getProfessional_area()).isEqualTo(DEFAULT_PROFESSIONAL_AREA);
        assertThat(testMember.getStarting_membership()).isEqualTo(DEFAULT_STARTING_MEMBERSHIP);
        assertThat(testMember.getEnding_membership()).isEqualTo(DEFAULT_ENDING_MEMBERSHIP);
        assertThat(testMember.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMember.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setName(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setLast_name(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setStatus(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setGender(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setBirthdate(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setPosition(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommitteeIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setCommittee(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfessional_areaIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setProfessional_area(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStarting_membershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setStarting_membership(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setEmail(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setPhone(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].committee").value(hasItem(DEFAULT_COMMITTEE.toString())))
            .andExpect(jsonPath("$.[*].professional_area").value(hasItem(DEFAULT_PROFESSIONAL_AREA.toString())))
            .andExpect(jsonPath("$.[*].starting_membership").value(hasItem(DEFAULT_STARTING_MEMBERSHIP.toString())))
            .andExpect(jsonPath("$.[*].ending_membership").value(hasItem(DEFAULT_ENDING_MEMBERSHIP.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.committee").value(DEFAULT_COMMITTEE.toString()))
            .andExpect(jsonPath("$.professional_area").value(DEFAULT_PROFESSIONAL_AREA.toString()))
            .andExpect(jsonPath("$.starting_membership").value(DEFAULT_STARTING_MEMBERSHIP.toString()))
            .andExpect(jsonPath("$.ending_membership").value(DEFAULT_ENDING_MEMBERSHIP.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .name(UPDATED_NAME)
            .last_name(UPDATED_LAST_NAME)
            .status(UPDATED_STATUS)
            .gender(UPDATED_GENDER)
            .birthdate(UPDATED_BIRTHDATE)
            .position(UPDATED_POSITION)
            .committee(UPDATED_COMMITTEE)
            .professional_area(UPDATED_PROFESSIONAL_AREA)
            .starting_membership(UPDATED_STARTING_MEMBERSHIP)
            .ending_membership(UPDATED_ENDING_MEMBERSHIP)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMember)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMember.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testMember.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testMember.getCommittee()).isEqualTo(UPDATED_COMMITTEE);
        assertThat(testMember.getProfessional_area()).isEqualTo(UPDATED_PROFESSIONAL_AREA);
        assertThat(testMember.getStarting_membership()).isEqualTo(UPDATED_STARTING_MEMBERSHIP);
        assertThat(testMember.getEnding_membership()).isEqualTo(UPDATED_ENDING_MEMBERSHIP);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = new Member();
        member1.setId(1L);
        Member member2 = new Member();
        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);
        member2.setId(2L);
        assertThat(member1).isNotEqualTo(member2);
        member1.setId(null);
        assertThat(member1).isNotEqualTo(member2);
    }
}
