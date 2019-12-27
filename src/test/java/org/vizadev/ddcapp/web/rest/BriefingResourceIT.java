package org.vizadev.ddcapp.web.rest;

import org.vizadev.ddcapp.JhipsterSampleApplicationApp;
import org.vizadev.ddcapp.domain.Briefing;
import org.vizadev.ddcapp.repository.BriefingRepository;
import org.vizadev.ddcapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.vizadev.ddcapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BriefingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BriefingResourceIT {

    private static final String DEFAULT_BRIEF_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_BRIEF_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SCORING = 1L;
    private static final Long UPDATED_SCORING = 2L;

    @Autowired
    private BriefingRepository briefingRepository;

    @Mock
    private BriefingRepository briefingRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBriefingMockMvc;

    private Briefing briefing;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BriefingResource briefingResource = new BriefingResource(briefingRepository);
        this.restBriefingMockMvc = MockMvcBuilders.standaloneSetup(briefingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Briefing createEntity(EntityManager em) {
        Briefing briefing = new Briefing()
            .briefTitle(DEFAULT_BRIEF_TITLE)
            .scoring(DEFAULT_SCORING);
        return briefing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Briefing createUpdatedEntity(EntityManager em) {
        Briefing briefing = new Briefing()
            .briefTitle(UPDATED_BRIEF_TITLE)
            .scoring(UPDATED_SCORING);
        return briefing;
    }

    @BeforeEach
    public void initTest() {
        briefing = createEntity(em);
    }

    @Test
    @Transactional
    public void createBriefing() throws Exception {
        int databaseSizeBeforeCreate = briefingRepository.findAll().size();

        // Create the Briefing
        restBriefingMockMvc.perform(post("/api/briefings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(briefing)))
            .andExpect(status().isCreated());

        // Validate the Briefing in the database
        List<Briefing> briefingList = briefingRepository.findAll();
        assertThat(briefingList).hasSize(databaseSizeBeforeCreate + 1);
        Briefing testBriefing = briefingList.get(briefingList.size() - 1);
        assertThat(testBriefing.getBriefTitle()).isEqualTo(DEFAULT_BRIEF_TITLE);
        assertThat(testBriefing.getScoring()).isEqualTo(DEFAULT_SCORING);
    }

    @Test
    @Transactional
    public void createBriefingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = briefingRepository.findAll().size();

        // Create the Briefing with an existing ID
        briefing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBriefingMockMvc.perform(post("/api/briefings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(briefing)))
            .andExpect(status().isBadRequest());

        // Validate the Briefing in the database
        List<Briefing> briefingList = briefingRepository.findAll();
        assertThat(briefingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBriefings() throws Exception {
        // Initialize the database
        briefingRepository.saveAndFlush(briefing);

        // Get all the briefingList
        restBriefingMockMvc.perform(get("/api/briefings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(briefing.getId().intValue())))
            .andExpect(jsonPath("$.[*].briefTitle").value(hasItem(DEFAULT_BRIEF_TITLE)))
            .andExpect(jsonPath("$.[*].scoring").value(hasItem(DEFAULT_SCORING.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBriefingsWithEagerRelationshipsIsEnabled() throws Exception {
        BriefingResource briefingResource = new BriefingResource(briefingRepositoryMock);
        when(briefingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBriefingMockMvc = MockMvcBuilders.standaloneSetup(briefingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBriefingMockMvc.perform(get("/api/briefings?eagerload=true"))
        .andExpect(status().isOk());

        verify(briefingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBriefingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        BriefingResource briefingResource = new BriefingResource(briefingRepositoryMock);
            when(briefingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBriefingMockMvc = MockMvcBuilders.standaloneSetup(briefingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBriefingMockMvc.perform(get("/api/briefings?eagerload=true"))
        .andExpect(status().isOk());

            verify(briefingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBriefing() throws Exception {
        // Initialize the database
        briefingRepository.saveAndFlush(briefing);

        // Get the briefing
        restBriefingMockMvc.perform(get("/api/briefings/{id}", briefing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(briefing.getId().intValue()))
            .andExpect(jsonPath("$.briefTitle").value(DEFAULT_BRIEF_TITLE))
            .andExpect(jsonPath("$.scoring").value(DEFAULT_SCORING.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBriefing() throws Exception {
        // Get the briefing
        restBriefingMockMvc.perform(get("/api/briefings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBriefing() throws Exception {
        // Initialize the database
        briefingRepository.saveAndFlush(briefing);

        int databaseSizeBeforeUpdate = briefingRepository.findAll().size();

        // Update the briefing
        Briefing updatedBriefing = briefingRepository.findById(briefing.getId()).get();
        // Disconnect from session so that the updates on updatedBriefing are not directly saved in db
        em.detach(updatedBriefing);
        updatedBriefing
            .briefTitle(UPDATED_BRIEF_TITLE)
            .scoring(UPDATED_SCORING);

        restBriefingMockMvc.perform(put("/api/briefings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBriefing)))
            .andExpect(status().isOk());

        // Validate the Briefing in the database
        List<Briefing> briefingList = briefingRepository.findAll();
        assertThat(briefingList).hasSize(databaseSizeBeforeUpdate);
        Briefing testBriefing = briefingList.get(briefingList.size() - 1);
        assertThat(testBriefing.getBriefTitle()).isEqualTo(UPDATED_BRIEF_TITLE);
        assertThat(testBriefing.getScoring()).isEqualTo(UPDATED_SCORING);
    }

    @Test
    @Transactional
    public void updateNonExistingBriefing() throws Exception {
        int databaseSizeBeforeUpdate = briefingRepository.findAll().size();

        // Create the Briefing

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBriefingMockMvc.perform(put("/api/briefings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(briefing)))
            .andExpect(status().isBadRequest());

        // Validate the Briefing in the database
        List<Briefing> briefingList = briefingRepository.findAll();
        assertThat(briefingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBriefing() throws Exception {
        // Initialize the database
        briefingRepository.saveAndFlush(briefing);

        int databaseSizeBeforeDelete = briefingRepository.findAll().size();

        // Delete the briefing
        restBriefingMockMvc.perform(delete("/api/briefings/{id}", briefing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Briefing> briefingList = briefingRepository.findAll();
        assertThat(briefingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
