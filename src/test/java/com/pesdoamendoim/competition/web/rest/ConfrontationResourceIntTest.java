package com.pesdoamendoim.competition.web.rest;

import com.pesdoamendoim.competition.PeSdoAmendoimApp;

import com.pesdoamendoim.competition.domain.Confrontation;
import com.pesdoamendoim.competition.repository.ConfrontationRepository;
import com.pesdoamendoim.competition.service.ConfrontationService;
import com.pesdoamendoim.competition.service.dto.ConfrontationDTO;
import com.pesdoamendoim.competition.service.mapper.ConfrontationMapper;
import com.pesdoamendoim.competition.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.pesdoamendoim.competition.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConfrontationResource REST controller.
 *
 * @see ConfrontationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeSdoAmendoimApp.class)
public class ConfrontationResourceIntTest {

    private static final Instant DEFAULT_DATA_CONFRONTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONFRONTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SCORE_PLAYER_1 = 1;
    private static final Integer UPDATED_SCORE_PLAYER_1 = 2;

    private static final Integer DEFAULT_SCORE_PLAYER_2 = 1;
    private static final Integer UPDATED_SCORE_PLAYER_2 = 2;

    private static final Integer DEFAULT_PENALTY_PLAYER_1 = 1;
    private static final Integer UPDATED_PENALTY_PLAYER_1 = 2;

    private static final Integer DEFAULT_PENALTY_PLAYER_2 = 1;
    private static final Integer UPDATED_PENALTY_PLAYER_2 = 2;

    @Autowired
    private ConfrontationRepository confrontationRepository;

    @Autowired
    private ConfrontationMapper confrontationMapper;

    @Autowired
    private ConfrontationService confrontationService;

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

    private MockMvc restConfrontationMockMvc;

    private Confrontation confrontation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfrontationResource confrontationResource = new ConfrontationResource(confrontationService);
        this.restConfrontationMockMvc = MockMvcBuilders.standaloneSetup(confrontationResource)
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
    public static Confrontation createEntity(EntityManager em) {
        Confrontation confrontation = new Confrontation()
            .dataConfronto(DEFAULT_DATA_CONFRONTO)
            .scorePlayer1(DEFAULT_SCORE_PLAYER_1)
            .scorePlayer2(DEFAULT_SCORE_PLAYER_2)
            .penaltyPlayer1(DEFAULT_PENALTY_PLAYER_1)
            .penaltyPlayer2(DEFAULT_PENALTY_PLAYER_2);
        return confrontation;
    }

    @Before
    public void initTest() {
        confrontation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfrontation() throws Exception {
        int databaseSizeBeforeCreate = confrontationRepository.findAll().size();

        // Create the Confrontation
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);
        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isCreated());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeCreate + 1);
        Confrontation testConfrontation = confrontationList.get(confrontationList.size() - 1);
        assertThat(testConfrontation.getDataConfronto()).isEqualTo(DEFAULT_DATA_CONFRONTO);
        assertThat(testConfrontation.getScorePlayer1()).isEqualTo(DEFAULT_SCORE_PLAYER_1);
        assertThat(testConfrontation.getScorePlayer2()).isEqualTo(DEFAULT_SCORE_PLAYER_2);
        assertThat(testConfrontation.getPenaltyPlayer1()).isEqualTo(DEFAULT_PENALTY_PLAYER_1);
        assertThat(testConfrontation.getPenaltyPlayer2()).isEqualTo(DEFAULT_PENALTY_PLAYER_2);
    }

    @Test
    @Transactional
    public void createConfrontationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = confrontationRepository.findAll().size();

        // Create the Confrontation with an existing ID
        confrontation.setId(1L);
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConfrontations() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        // Get all the confrontationList
        restConfrontationMockMvc.perform(get("/api/confrontations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confrontation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataConfronto").value(hasItem(DEFAULT_DATA_CONFRONTO.toString())))
            .andExpect(jsonPath("$.[*].scorePlayer1").value(hasItem(DEFAULT_SCORE_PLAYER_1)))
            .andExpect(jsonPath("$.[*].scorePlayer2").value(hasItem(DEFAULT_SCORE_PLAYER_2)))
            .andExpect(jsonPath("$.[*].penaltyPlayer1").value(hasItem(DEFAULT_PENALTY_PLAYER_1)))
            .andExpect(jsonPath("$.[*].penaltyPlayer2").value(hasItem(DEFAULT_PENALTY_PLAYER_2)));
    }
    
    @Test
    @Transactional
    public void getConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        // Get the confrontation
        restConfrontationMockMvc.perform(get("/api/confrontations/{id}", confrontation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(confrontation.getId().intValue()))
            .andExpect(jsonPath("$.dataConfronto").value(DEFAULT_DATA_CONFRONTO.toString()))
            .andExpect(jsonPath("$.scorePlayer1").value(DEFAULT_SCORE_PLAYER_1))
            .andExpect(jsonPath("$.scorePlayer2").value(DEFAULT_SCORE_PLAYER_2))
            .andExpect(jsonPath("$.penaltyPlayer1").value(DEFAULT_PENALTY_PLAYER_1))
            .andExpect(jsonPath("$.penaltyPlayer2").value(DEFAULT_PENALTY_PLAYER_2));
    }

    @Test
    @Transactional
    public void getNonExistingConfrontation() throws Exception {
        // Get the confrontation
        restConfrontationMockMvc.perform(get("/api/confrontations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        int databaseSizeBeforeUpdate = confrontationRepository.findAll().size();

        // Update the confrontation
        Confrontation updatedConfrontation = confrontationRepository.findById(confrontation.getId()).get();
        // Disconnect from session so that the updates on updatedConfrontation are not directly saved in db
        em.detach(updatedConfrontation);
        updatedConfrontation
            .dataConfronto(UPDATED_DATA_CONFRONTO)
            .scorePlayer1(UPDATED_SCORE_PLAYER_1)
            .scorePlayer2(UPDATED_SCORE_PLAYER_2)
            .penaltyPlayer1(UPDATED_PENALTY_PLAYER_1)
            .penaltyPlayer2(UPDATED_PENALTY_PLAYER_2);
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(updatedConfrontation);

        restConfrontationMockMvc.perform(put("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isOk());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeUpdate);
        Confrontation testConfrontation = confrontationList.get(confrontationList.size() - 1);
        assertThat(testConfrontation.getDataConfronto()).isEqualTo(UPDATED_DATA_CONFRONTO);
        assertThat(testConfrontation.getScorePlayer1()).isEqualTo(UPDATED_SCORE_PLAYER_1);
        assertThat(testConfrontation.getScorePlayer2()).isEqualTo(UPDATED_SCORE_PLAYER_2);
        assertThat(testConfrontation.getPenaltyPlayer1()).isEqualTo(UPDATED_PENALTY_PLAYER_1);
        assertThat(testConfrontation.getPenaltyPlayer2()).isEqualTo(UPDATED_PENALTY_PLAYER_2);
    }

    @Test
    @Transactional
    public void updateNonExistingConfrontation() throws Exception {
        int databaseSizeBeforeUpdate = confrontationRepository.findAll().size();

        // Create the Confrontation
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfrontationMockMvc.perform(put("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        int databaseSizeBeforeDelete = confrontationRepository.findAll().size();

        // Delete the confrontation
        restConfrontationMockMvc.perform(delete("/api/confrontations/{id}", confrontation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Confrontation.class);
        Confrontation confrontation1 = new Confrontation();
        confrontation1.setId(1L);
        Confrontation confrontation2 = new Confrontation();
        confrontation2.setId(confrontation1.getId());
        assertThat(confrontation1).isEqualTo(confrontation2);
        confrontation2.setId(2L);
        assertThat(confrontation1).isNotEqualTo(confrontation2);
        confrontation1.setId(null);
        assertThat(confrontation1).isNotEqualTo(confrontation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfrontationDTO.class);
        ConfrontationDTO confrontationDTO1 = new ConfrontationDTO();
        confrontationDTO1.setId(1L);
        ConfrontationDTO confrontationDTO2 = new ConfrontationDTO();
        assertThat(confrontationDTO1).isNotEqualTo(confrontationDTO2);
        confrontationDTO2.setId(confrontationDTO1.getId());
        assertThat(confrontationDTO1).isEqualTo(confrontationDTO2);
        confrontationDTO2.setId(2L);
        assertThat(confrontationDTO1).isNotEqualTo(confrontationDTO2);
        confrontationDTO1.setId(null);
        assertThat(confrontationDTO1).isNotEqualTo(confrontationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(confrontationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(confrontationMapper.fromId(null)).isNull();
    }
}
