package org.vizadev.ddcapp.web.rest;

import org.vizadev.ddcapp.JhipsterSampleApplicationApp;
import org.vizadev.ddcapp.domain.BriefingHistory;
import org.vizadev.ddcapp.repository.BriefingHistoryRepository;
import org.vizadev.ddcapp.service.BriefingHistoryService;
import org.vizadev.ddcapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.vizadev.ddcapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.vizadev.ddcapp.domain.enumeration.TypeOfBriefing;
/**
 * Integration tests for the {@link BriefingHistoryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BriefingHistoryResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TypeOfBriefing DEFAULT_TYPE_OF_BRIEFING = TypeOfBriefing.INTROBR;
    private static final TypeOfBriefing UPDATED_TYPE_OF_BRIEFING = TypeOfBriefing.WORKBR;

    @Autowired
    private BriefingHistoryRepository briefingHistoryRepository;

    @Autowired
    private BriefingHistoryService briefingHistoryService;

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

    private MockMvc restBriefingHistoryMockMvc;

    private BriefingHistory briefingHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BriefingHistoryResource briefingHistoryResource = new BriefingHistoryResource(briefingHistoryService);
        this.restBriefingHistoryMockMvc = MockMvcBuilders.standaloneSetup(briefingHistoryResource)
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
    public static BriefingHistory createEntity(EntityManager em) {
        BriefingHistory briefingHistory = new BriefingHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .typeOfBriefing(DEFAULT_TYPE_OF_BRIEFING);
        return briefingHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BriefingHistory createUpdatedEntity(EntityManager em) {
        BriefingHistory briefingHistory = new BriefingHistory()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .typeOfBriefing(UPDATED_TYPE_OF_BRIEFING);
        return briefingHistory;
    }

    @BeforeEach
    public void initTest() {
        briefingHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBriefingHistory() throws Exception {
        int databaseSizeBeforeCreate = briefingHistoryRepository.findAll().size();

        // Create the BriefingHistory
        restBriefingHistoryMockMvc.perform(post("/api/briefing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(briefingHistory)))
            .andExpect(status().isCreated());

        // Validate the BriefingHistory in the database
        List<BriefingHistory> briefingHistoryList = briefingHistoryRepository.findAll();
        assertThat(briefingHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        BriefingHistory testBriefingHistory = briefingHistoryList.get(briefingHistoryList.size() - 1);
        assertThat(testBriefingHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBriefingHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBriefingHistory.getTypeOfBriefing()).isEqualTo(DEFAULT_TYPE_OF_BRIEFING);
    }

    @Test
    @Transactional
    public void createBriefingHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = briefingHistoryRepository.findAll().size();

        // Create the BriefingHistory with an existing ID
        briefingHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBriefingHistoryMockMvc.perform(post("/api/briefing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(briefingHistory)))
            .andExpect(status().isBadRequest());

        // Validate the BriefingHistory in the database
        List<BriefingHistory> briefingHistoryList = briefingHistoryRepository.findAll();
        assertThat(briefingHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBriefingHistories() throws Exception {
        // Initialize the database
        briefingHistoryRepository.saveAndFlush(briefingHistory);

        // Get all the briefingHistoryList
        restBriefingHistoryMockMvc.perform(get("/api/briefing-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(briefingHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].typeOfBriefing").value(hasItem(DEFAULT_TYPE_OF_BRIEFING.toString())));
    }
    
    @Test
    @Transactional
    public void getBriefingHistory() throws Exception {
        // Initialize the database
        briefingHistoryRepository.saveAndFlush(briefingHistory);

        // Get the briefingHistory
        restBriefingHistoryMockMvc.perform(get("/api/briefing-histories/{id}", briefingHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(briefingHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.typeOfBriefing").value(DEFAULT_TYPE_OF_BRIEFING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBriefingHistory() throws Exception {
        // Get the briefingHistory
        restBriefingHistoryMockMvc.perform(get("/api/briefing-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBriefingHistory() throws Exception {
        // Initialize the database
        briefingHistoryService.save(briefingHistory);

        int databaseSizeBeforeUpdate = briefingHistoryRepository.findAll().size();

        // Update the briefingHistory
        BriefingHistory updatedBriefingHistory = briefingHistoryRepository.findById(briefingHistory.getId()).get();
        // Disconnect from session so that the updates on updatedBriefingHistory are not directly saved in db
        em.detach(updatedBriefingHistory);
        updatedBriefingHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .typeOfBriefing(UPDATED_TYPE_OF_BRIEFING);

        restBriefingHistoryMockMvc.perform(put("/api/briefing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBriefingHistory)))
            .andExpect(status().isOk());

        // Validate the BriefingHistory in the database
        List<BriefingHistory> briefingHistoryList = briefingHistoryRepository.findAll();
        assertThat(briefingHistoryList).hasSize(databaseSizeBeforeUpdate);
        BriefingHistory testBriefingHistory = briefingHistoryList.get(briefingHistoryList.size() - 1);
        assertThat(testBriefingHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBriefingHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBriefingHistory.getTypeOfBriefing()).isEqualTo(UPDATED_TYPE_OF_BRIEFING);
    }

    @Test
    @Transactional
    public void updateNonExistingBriefingHistory() throws Exception {
        int databaseSizeBeforeUpdate = briefingHistoryRepository.findAll().size();

        // Create the BriefingHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBriefingHistoryMockMvc.perform(put("/api/briefing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(briefingHistory)))
            .andExpect(status().isBadRequest());

        // Validate the BriefingHistory in the database
        List<BriefingHistory> briefingHistoryList = briefingHistoryRepository.findAll();
        assertThat(briefingHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBriefingHistory() throws Exception {
        // Initialize the database
        briefingHistoryService.save(briefingHistory);

        int databaseSizeBeforeDelete = briefingHistoryRepository.findAll().size();

        // Delete the briefingHistory
        restBriefingHistoryMockMvc.perform(delete("/api/briefing-histories/{id}", briefingHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BriefingHistory> briefingHistoryList = briefingHistoryRepository.findAll();
        assertThat(briefingHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
